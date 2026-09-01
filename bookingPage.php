<?php
global $conn;
session_start();
require 'db_connector.php';
include 'includes/header.php';

if (!isset($_SESSION['cart']) || !is_array($_SESSION['cart'])) {
    $_SESSION['cart'] = [];
}

$cartItems = $_SESSION['cart'];
$message = '';

//remove item
if (isset($_GET['remove'])) {
    $removeID = (int)$_GET['remove'];
    unset($_SESSION['cart'][$removeID]);
    header("Location: bookingPage.php");
    exit;
}

//update checkout
if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    //checkout
    if (isset($_POST['checkout'])) {
        $name = trim($_POST['name']);
        $email = trim($_POST['email']);
        $phone = trim($_POST['phone']);
        $errors = [];

        if ($name === '' || $email === '' || $phone === '') {
            $errors[] = "Please fill in all fields.";
        }

        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $errors[] = "Invalid email format.";
        }

        if (empty($errors)) {
            foreach ($cartItems as $tourID => $dates) {
                if (!is_array($dates)) continue;
                foreach ($dates as $tourDate => $item) {
                    $quantity = (int)($item['quantity'] ?? 0);
                    if ($quantity <= 0 || empty($tourDate)) continue;

                    $stmt = $conn->prepare(
                            "INSERT INTO orders (name, email, phone, groupSize, tourID, tourDate) VALUES (?, ?, ?, ?, ?, ?)"
                    );
                    $stmt->bind_param("sssiss", $name, $email, $phone, $quantity, $tourID, $tourDate);
                    $stmt->execute();
                    $stmt->close();


                    $stmtCap = $conn->prepare("SELECT spacesLeft FROM tours WHERE tourID = ?");
                    $stmtCap->bind_param("i", $tourID);
                    $stmtCap->execute();
                    $resCap = $stmtCap->get_result();
                    $tourData = $resCap->fetch_assoc();
                    $stmtCap->close();

                    $maxSpaces = (int)$tourData['spacesLeft'];
                    $stmtCheck = $conn->prepare("SELECT spacesLeft FROM tour_dates WHERE tourID = ? AND tourDate = ?");
                    $stmtCheck->bind_param("is", $tourID, $tourDate);
                    $stmtCheck->execute();
                    $resultCheck = $stmtCheck->get_result();
                    $row = $resultCheck->fetch_assoc();
                    $stmtCheck->close();

                    if ($row) {
                        $newSpaces = max(0, $row['spacesLeft'] - $quantity);
                        $stmtUpdate = $conn->prepare(
                                "UPDATE tour_dates SET spacesLeft = ? WHERE tourID = ? AND tourDate = ?"
                        );
                        $stmtUpdate->bind_param("iis", $newSpaces, $tourID, $tourDate);
                        $stmtUpdate->execute();
                        $stmtUpdate->close();
                    } else {
                        $remaining = max(0, $maxSpaces - $quantity);
                        $stmtInsert = $conn->prepare(
                                "INSERT INTO tour_dates (tourID, tourDate, spacesLeft) VALUES (?, ?, ?)"
                        );
                        $stmtInsert->bind_param("isi", $tourID, $tourDate, $remaining);
                        $stmtInsert->execute();
                        $stmtInsert->close();
                    }

                }
            }

            $_SESSION['cart'] = [];
            $cartItems = [];
            $message = "Booking placed successfully!";
        }
    }

    //update cart
    if (!empty($_POST['quantity']) && is_array($_POST['quantity'])) {
        foreach ($_POST['quantity'] as $tourID => $dates) {
            if (!is_array($dates)) continue;
            foreach ($dates as $tourDate => $qty) {
                if (isset($_SESSION['cart'][$tourID][$tourDate])) {
                    $_SESSION['cart'][$tourID][$tourDate]['quantity'] = max(1, (int)$qty);
                }
            }
        }
    }
}

$total = 0;
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<h2>Your Cart</h2>

<?php if ($message) echo "<p style='color:green;'>$message</p>"; ?>

<?php if (empty($cartItems)): ?>
    <p>Your cart is empty. <a href="toursListPage.php">Browse Tours</a></p>
<?php else: ?>
    <form method="post">
        <table border="1" cellpadding="10">
            <tr>
                <th>Tour</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Date</th>
                <th>Subtotal</th>
                <th>Remove</th>
            </tr>
            <?php
            foreach ($cartItems as $tourID => $dates):
                if (!is_array($dates)) continue;
                foreach ($dates as $tourDate => $item):
                    $tourTitle = $item['tourTitle'] ?? 'Unknown';
                    $price = (float)($item['price'] ?? 0);
                    $quantity = (int)($item['quantity'] ?? 0);
                    $subtotal = $price * $quantity;
                    $total += $subtotal;
                    ?>
                    <tr>
                        <td><?= htmlspecialchars($tourTitle) ?></td>
                        <td>£<?= number_format($price,2) ?></td>
                        <td>
                            <input type="number" name="quantity[<?= $tourID ?>][<?= htmlspecialchars($tourDate) ?>]"
                                   value="<?= $quantity ?>" min="1">
                        </td>
                        <td><?= htmlspecialchars($tourDate) ?></td>
                        <td>£<?= number_format($subtotal,2) ?></td>
                        <td><a href="bookingPage.php?remove=<?= $tourID ?>">Remove</a></td>
                    </tr>
                <?php
                endforeach;
            endforeach;
            ?>
            <tr>
                <td colspan="4"><strong>Total</strong></td>
                <td colspan="2">£<?= number_format($total,2) ?></td>
            </tr>
        </table>
        <br>
        <button type="submit">Update Cart</button>
    </form>

    <h3>Checkout</h3>
    <form method="post">
        <input type="hidden" name="checkout" value="1">
        <label>Name:<br><input type="text" name="name" required></label><br>
        <label>Email:<br><input type="email" name="email" required></label><br>
        <label>Phone:<br><input type="text" name="phone" required></label><br><br>
        <button type="submit">Place Booking</button>
    </form>
<?php endif; ?>
<?php include 'includes/footer.php'; ?>

</body>
</html>

