<?php
global $conn;
session_start();
require 'db_connector.php';
include 'includes/header.php';

if (!isset($_SESSION['admin_id'])) {
    header('Location: login.php');
    exit;
}
if (isset($_GET['cancel_booking'])) {
    $bookingID = (int)$_GET['cancel_booking'];
    $stmt = $conn->prepare("SELECT tourID, tourDate, groupSize FROM orders WHERE userID=?");
    $stmt->bind_param("i", $bookingID);
    $stmt->execute();
    $result = $stmt->get_result();
    $booking = $result->fetch_assoc();
    $stmt->close();

    if ($booking) {
        $tourID   = $booking['tourID'];
        $tourDate = $booking['tourDate'];
        $groupSize = $booking['groupSize'];
        $stmt = $conn->prepare("UPDATE tour_dates SET spacesLeft = spacesLeft + ? 
                                WHERE tourID = ? AND tourDate = ?");
        $stmt->bind_param("iis", $groupSize, $tourID, $tourDate);
        $stmt->execute();
        $stmt->close();
        $stmt = $conn->prepare("DELETE FROM orders WHERE userID=?");
        $stmt->bind_param("i", $bookingID);
        $stmt->execute();
        $stmt->close();

        $message = "Booking removed and spaces restored successfully.";
    }
}

//add tour
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action']) && $_POST['action'] === 'add') {
    $title = $_POST['title'];
    $price = $_POST['price'];
    $description = $_POST['description'];
    $short_desc = $_POST['short_desc'];
    $spacesLeft = $_POST['spacesLeft'];
    $tourSize = $_POST['tourSize'];
    $startPoint = $_POST['startPoint'];
    $rating = $_POST['rating'];
    $adminID = $_SESSION['admin_id'];

    //image upload
    $imageLink = '';
    if (!empty($_FILES['image']['name'])) {
        $uploadDir = 'uploads/';
        $imageName = uniqid() . '-' . basename($_FILES['image']['name']);
        $targetFile = $uploadDir . $imageName;
        if (move_uploaded_file($_FILES['image']['tmp_name'], $targetFile)) {
            $imageLink = $targetFile;
        }
    }

    $stmt = $conn->prepare("INSERT INTO tours (tourTitle, price, description, imageLink, spacesLeft, tourSize, startPoint, rating, adminID, short_desc) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("sdsssiiiss", $title, $price, $description, $imageLink, $spacesLeft, $tourSize, $startPoint, $rating, $adminID, $short_desc);
    $stmt->execute();
}

//delete tour
if (isset($_GET['delete'])) {
    $tourID = (int)$_GET['delete'];
    $stmtImg = $conn->prepare("SELECT imageLink FROM tours WHERE tourID=?");
    $stmtImg->bind_param("i", $tourID);
    $stmtImg->execute();
    $resImg = $stmtImg->get_result();
    if ($row = $resImg->fetch_assoc()) {
        if ($row['imageLink'] && file_exists($row['imageLink'])) {
            unlink($row['imageLink']);
        }
    }
    $stmtImg->close();
    $stmt = $conn->prepare("DELETE FROM tours WHERE tourID=?");
    $stmt->bind_param("i", $tourID);
    $stmt->execute();
}
if (isset($_GET['cancel_booking'])) {
    $bookingID = (int)$_GET['cancel_booking'];
    $stmt = $conn->prepare("DELETE FROM orders WHERE userID=?");
    $stmt->bind_param("i", $bookingID);
    $stmt->execute();
}
$tours = $conn->query("SELECT * FROM tours ORDER BY tourID ASC");
$bookings = $conn->query("
    SELECT o.*, t.tourTitle 
    FROM orders o 
    LEFT JOIN tours t ON o.tourID = t.tourID 
    ORDER BY o.userID DESC
");
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<div class="container">
    <h1>Admin Dashboard</h1>

    <!-- Add Tour Form -->
    <h2>Add New Tour</h2>
    <form method="post" enctype="multipart/form-data" class="card">
        <input type="hidden" name="action" value="add">
        <label>Title: <input name="title" required></label><br>
        <label>Price: <input type="number" name="price" step="0.01" required></label><br>
        <label>Description:<br><textarea name="description" required></textarea></label><br>
        <label>Short Description:<br><textarea name="short_desc" required></textarea></label><br>
        <label>Spaces Left: <input type="number" name="spacesLeft" min="0" required></label><br>
        <label>Tour Size: <input type="number" name="tourSize" min="1" required></label><br>
        <label>Start Point: <input name="startPoint" required></label><br>
        <label>Rating: <input type="number" name="rating" min="1" max="5" required></label><br>
        <label>Image: <input type="file" name="image"></label><br>
        <button type="submit">Add Tour</button>
    </form>

    <!-- Tours List -->
    <h2>Existing Tours</h2>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th><th>Title</th><th>Price</th><th>Actions</th>
        </tr>
        <?php while ($row = $tours->fetch_assoc()): ?>
            <tr>
                <td><?= $row['tourID'] ?></td>
                <td><?= htmlspecialchars($row['tourTitle']) ?></td>
                <td>£<?= number_format($row['price'],2) ?></td>
                <td>
                    <a href="editTour.php?id=<?= $row['tourID'] ?>"class = "btn">Edit</a> |
                    <a href="dashboard.php?delete=<?= $row['tourID'] ?>" onclick="return confirm('Are you sure?')"class = "btn">Delete</a>
                </td>
            </tr>
        <?php endwhile; ?>
    </table>

    <!--bookings-->
    <h2>Bookings</h2>
    <table border="1" cellpadding="5">
        <tr>
            <th>User ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Tour</th><th>Group Size</th><th>Date</th><th>Actions</th>
        </tr>
        <?php while ($row = $bookings->fetch_assoc()): ?>
            <tr>
                <td><?= $row['userID'] ?></td>
                <td><?= htmlspecialchars($row['name']) ?></td>
                <td><?= htmlspecialchars($row['email']) ?></td>
                <td><?= htmlspecialchars($row['phone']) ?></td>
                <td><?= htmlspecialchars($row['tourTitle']) ?></td>
                <td><?= $row['groupSize'] ?></td>
                <td><?= htmlspecialchars($row['tourDate'])?></td>
                <td>
                    <a href="dashboard.php?cancel_booking=<?= $row['userID'] ?>"
                       onclick="return confirm('Are you sure you want to remove this booking?')">Remove</a>
                </td>
            </tr>
        <?php endwhile; ?>
    </table>


</div>
</body>
</html>
