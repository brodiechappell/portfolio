<?php
global $conn;
session_start();
require 'db_connector.php';
include 'includes/header.php';

if (!isset($_SESSION['cart']) || !is_array($_SESSION['cart'])) {
    $_SESSION['cart'] = [];
}
foreach ($_SESSION['cart'] as $tourID => $item) {
    if (isset($item['tourTitle'])) {
        $oldQty = $item['quantity'] ?? 1;
        $oldTitle = $item['tourTitle'];
        $oldPrice = $item['price'] ?? 0;

        $_SESSION['cart'][$tourID] = [
                date('Y-m-d') => [
                        'tourTitle' => $oldTitle,
                        'price' => $oldPrice,
                        'quantity' => $oldQty
                ]
        ];
    }
}
//add to cart
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['tourID'], $_POST['tourDate'])) {
    $tourID = (int)$_POST['tourID'];
    $tourDate = $_POST['tourDate'];
    $groupSize = max(1, (int)($_POST['groupSize'] ?? 1));
    $stmt = $conn->prepare("SELECT tourTitle, price FROM tours WHERE tourID = ?");
    $stmt->bind_param("i", $tourID);
    $stmt->execute();
    $tour = $stmt->get_result()->fetch_assoc();
    $stmt->close();

    if ($tour) {
        if (!isset($_SESSION['cart'][$tourID])) {
            $_SESSION['cart'][$tourID] = [];
        }

        if (isset($_SESSION['cart'][$tourID][$tourDate])) {
            $_SESSION['cart'][$tourID][$tourDate]['quantity'] += $groupSize;
        } else {
            $_SESSION['cart'][$tourID][$tourDate] = [
                    'tourTitle' => $tour['tourTitle'],
                    'price' => $tour['price'],
                    'quantity' => $groupSize
            ];
        }
        $message = "Tour added to cart for $tourDate!";
    }
}
$result = $conn->query("SELECT * FROM tours ORDER BY tourID ASC");
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Available Tours</title>
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<div class="container">
    <h2>Available Tours</h2>

    <?php if (!empty($message)) echo "<p class='success-message'>$message</p>"; ?>

    <div class="tour-grid">
        <?php while ($tour = $result->fetch_assoc()): ?>
            <div class="tour-card">
                <?php if ($tour['imageLink']): ?>
                    <img src="<?= htmlspecialchars($tour['imageLink']) ?>" alt="<?= htmlspecialchars($tour['tourTitle']) ?>" class="tour-thumb">
                <?php endif; ?>
                <h3><?= htmlspecialchars($tour['tourTitle']) ?></h3>
                <p><?= htmlspecialchars($tour['short_desc']) ?></p>
                <p><strong>Price:</strong> £<?= number_format($tour['price'], 2) ?></p>
                <div class="button-row">
                    <a href="tour.php?tourID=<?= $tour['tourID'] ?>" class="btn btn-details">View Details</a>
                </div>
                <form method="post">
                    <input type="hidden" name="tourID" value="<?= $tour['tourID'] ?>">
                    <label>Group Size:
                        <input type="number" name="groupSize" value="1" min="1">
                    </label><br>
                    <label>Select Date:
                        <input type="date" name="tourDate" required>
                    </label><br>
                    <button type="submit">Add to Cart</button>
                </form>
            </div>
        <?php endwhile; ?>
    </div>

    <p><a href="bookingPage.php">View Cart (<?= count($_SESSION['cart']) ?> items)</a></p>
    <?php include 'includes/footer.php'; ?>

</div>
<script>
    window.__TOURS__ = <?= json_encode($tours ?? []) ?>;
    window.__CART__ = <?= json_encode($_SESSION['cart'] ?? []) ?>;
</script>
<script src="app.js"></script>
<script>
    const pager = new Site.Pager('tourContainer', 6);
    pager.render();
    Site.setupAddToCart('tourContainer');
    Site.updateCartCounter();
</script>

</body>
</html>
