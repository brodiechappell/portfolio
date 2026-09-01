<?php
global $conn;
session_start();
require 'db_connector.php';
include 'includes/header.php';

if (!isset($_SESSION['admin_id'])) {
    header('Location: login.php');
    exit;
}

if (!isset($_GET['id'])) {
    die("No tour selected.");
}

$tourID = (int)$_GET['id'];
$stmt = $conn->prepare("SELECT * FROM tours WHERE tourID = ?");
$stmt->bind_param("i", $tourID);
$stmt->execute();
$tourResult = $stmt->get_result();
$tour = $tourResult->fetch_assoc();

if (!$tour) {
    die("Tour not found.");
}
$message = '';
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title       = $_POST['tourTitle'] ?? '';
    $price       = $_POST['price'] ?? 0;
    $description = $_POST['description'] ?? '';
    $short_desc  = $_POST['short_desc'] ?? '';
    $spacesLeft  = $_POST['spacesLeft'] ?? -1;
    $tourSize    = $_POST['tourSize'] ?? -1;
    $startPoint  = $_POST['startPoint'] ?? '';
    $rating      = $_POST['rating'] ?? 0;
    $adminID     = $_SESSION['admin_id'];

    $imageLink = $tour['imageLink'];

    //delete image
    if (isset($_POST['remove_image']) && !empty($imageLink) && file_exists($imageLink)) {
        unlink($imageLink);
        $imageLink = null;
    }

    //new image
    if (isset($_FILES['image']) && $_FILES['image']['error'] === UPLOAD_ERR_OK) {
        $targetDir = 'uploads/';
        if (!is_dir($targetDir)) {
            mkdir($targetDir, 0755, true);
        }
        $fileName = uniqid() . '-' . basename($_FILES['image']['name']);
        $targetFile = $targetDir . $fileName;
        if (move_uploaded_file($_FILES['image']['tmp_name'], $targetFile)) {
            $imageLink = $targetFile;
        }
    }

    $stmtUpdate = $conn->prepare("UPDATE tours 
        SET tourTitle=?, price=?, description=?, imageLink=?, spacesLeft=?, tourSize=?, startPoint=?, rating=?, adminID=?, short_desc=?
        WHERE tourID=?");
    $stmtUpdate->bind_param(
            "sdssiisissi",
            $title, $price, $description, $imageLink, $spacesLeft, $tourSize, $startPoint, $rating, $adminID, $short_desc, $tourID
    );

    $stmtUpdate->execute();

    $message = "Tour updated successfully!";
    $tour['imageLink'] = $imageLink;
}
?>

<div class="container">
    <h2>Edit Tour: <?= htmlspecialchars($tour['tourTitle']) ?></h2>
    <link rel="stylesheet" href="style/style.css">
    <?php if (!empty($message)) echo "<p style='color:green;'>$message</p>"; ?>

    <form method="post" enctype="multipart/form-data">
        <label>Title:<br>
            <input type="text" name="tourTitle" value="<?= htmlspecialchars($tour['tourTitle']) ?>" required>
        </label><br><br>

        <label>Price:<br>
            <input type="number" name="price" value="<?= htmlspecialchars($tour['price']) ?>" step="0.01" required>
        </label><br><br>

        <label>Description:<br>
            <textarea name="description" rows="5"><?= htmlspecialchars($tour['description']) ?></textarea>
        </label><br><br>

        <?php if (!empty($tour['imageLink'])): ?>
            <p>Current Image:</p>
            <img src="<?= htmlspecialchars($tour['imageLink']) ?>" alt="<?= htmlspecialchars($tour['tourTitle']) ?>" style="max-width:200px;"><br>
            <label><input type="checkbox" name="remove_image" value="1"> Remove Image</label><br><br>
        <?php endif; ?>

        <label>Upload New Image:<br>
            <input type="file" name="image">
        </label><br><br>

        <label>Spaces Left:<br>
            <input type="number" name="spacesLeft" value="<?= htmlspecialchars($tour['spacesLeft']) ?>">
        </label><br><br>

        <label>Tour Size:<br>
            <input type="number" name="tourSize" value="<?= htmlspecialchars($tour['tourSize']) ?>">
        </label><br><br>

        <label>Start Point:<br>
            <input type="text" name="startPoint" value="<?= htmlspecialchars($tour['startPoint']) ?>">
        </label><br><br>

        <label>Rating (0-5):<br>
            <input type="number" name="rating" min="0" max="5" value="<?= htmlspecialchars($tour['rating']) ?>">
        </label><br><br>

        <label>Short Description:<br>
            <textarea name="short_desc"><?= htmlspecialchars($tour['short_desc']) ?></textarea>
        </label><br><br>

        <button type="submit">Update Tour</button>
        <p><a href="dashboard.php" class="btn">Back to Dashboard</a></p>

    </form>
    <?php include 'includes/footer.php'; ?>

</div>
