<?php
// header.php
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><?= isset($pageTitle) ? htmlspecialchars($pageTitle) : 'nadair tours' ?></title>
    <link rel="stylesheet" href="../style/style.css">
</head>
<body>
<header>
    <div class="header-container">
        <h1><a href="index.php">nadair tours</a></h1>
        <nav>
            <a href="index.php">Home</a>
            <a href="toursListPage.php">Tours</a>
            <a href="bookingPage.php">Cart (<?= isset($_SESSION['cart']) ? count($_SESSION['cart']) : 0 ?>)</a>
            <a href="login.php">Admin login</a>
        </nav>
    </div>
</header>
<main>
