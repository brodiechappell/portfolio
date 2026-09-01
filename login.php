<?php
global $conn;
session_start();
require 'db_connector.php';
include 'includes/header.php';

$error = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = trim($_POST['username'] ?? '');
    $password = $_POST['password'] ?? '';

    if ($username === '' || $password === '') {
        $error = 'Please provide username and password.';
    } else {
        $stmt = $conn->prepare("SELECT adminID, username, password FROM admin WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $res = $stmt->get_result();
        $row = $res->fetch_assoc();

        if ($row && hash_equals($row['password'], $password)) {
            $_SESSION['admin_id'] = $row['adminID'];
            $_SESSION['username'] = $row['username'];
            header('Location: dashboard.php');
            exit;
        } else {
            $error = 'Invalid username or password.';
        }
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<div class="container">
    <h2>Admin Login</h2>
    <?php if ($error) echo "<div class='error'>".htmlspecialchars($error)."</div>"; ?>
    <?php if (isset($error)): ?>
        <p style="color:red; font-weight:bold;"><?= htmlspecialchars($error) ?></p>
    <?php endif; ?>
    <form method="post">
        <label>Username<br><input type="text" name="username" required></label><br>
        <label>Password<br><input type="password" name="password" required></label><br>
        <button type="submit">Login</button>
    </form>
    <?php include 'includes/footer.php'; ?>

</div>
</body>
</html>

