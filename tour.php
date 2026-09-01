<?php
global $conn;
session_start();
require 'db_connector.php';
include 'includes/header.php';

$tourID = (int)($_GET['tourID'] ?? 0);
if ($tourID <= 0) die("Invalid tour.");
$stmt = $conn->prepare("SELECT * FROM tours WHERE tourID = ?");
$stmt->bind_param("i", $tourID);
$stmt->execute();
$tour = $stmt->get_result()->fetch_assoc();
$stmt->close();

if (!$tour) die("Tour not found.");
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $groupSize = max(1, (int)($_POST['groupSize'] ?? 1));
    $tourDate = $_POST['tourDate'] ?? date('Y-m-d');
    if (!isset($_SESSION['cart'][$tourID])) {
        $_SESSION['cart'][$tourID] = [];
    }
    if (isset($_SESSION['cart'][$tourID][$tourDate])) {
        $_SESSION['cart'][$tourID][$tourDate]['quantity'] += $groupSize;
    } else {
        $_SESSION['cart'][$tourID][$tourDate] = [
                'tourTitle' => $tour['tourTitle'],
                'price'     => $tour['price'],
                'imageLink' => $tour['imageLink'],
                'quantity'  => $groupSize
        ];
    }

    header("Location: tour.php?tourID=$tourID&added=1");
    exit;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<div class="container">
    <h2><?= htmlspecialchars($tour['tourTitle']) ?></h2>

    <?php if (isset($_GET['added'])): ?>
        <p class="success-message">Tour added to cart!</p>
    <?php endif; ?>

    <p><strong>Price:</strong> £<?= number_format($tour['price'], 2) ?></p>
    <p><?= nl2br(htmlspecialchars($tour['description'])) ?></p>

    <?php if (!empty($tour['imageLink'])): ?>
        <img src="<?= htmlspecialchars($tour['imageLink']) ?>" style="max-width:100%; border-radius:10px;">
    <?php endif; ?>
    <form method="post">
        <label>Group Size:
            <input type="number" name="groupSize" value="1" min="1">
        </label><br><br>

        <label>Select Date:
            <input type="date" name="tourDate" required>
        </label><br><br>

        <button type="submit" class="btn">Add to Cart</button>
    </form>

    <p><a href="bookingPage.php" class="btn">View Cart</a></p>
    <p><a href="toursListPage.php" class="btn">Back to Tours</a></p>
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
<?php include 'includes/footer.php'; ?>
</body>
</html>