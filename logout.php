<?php
require 'db_connector.php';
unset($_SESSION['admin']);
header('Location: login.php');
