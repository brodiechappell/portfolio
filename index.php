<?php
require 'db_connector.php';
include 'includes/header.php';
?>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>N`adair Tours — Eco Travel</title>
    <link rel="stylesheet" href="style/style.css">
</head>
<body>
<div class="container">

    <section class="hero card">
        <h2>Eco-friendly travel with N`adair</h2>
        <p class="small">Hello and welcome to N`adair Tours where we take you on guided tours that highlight Scotlands cultural heritage, natural landscapes and local communities while encouraging responsible travel practices that support the UN
            Sustainable Development Goals </p>
        <p><a class="btn" href="toursListPage.php">Browse Tours</a></p>
    </section>

    <section>
        <h3>Our commitment to sustainability</h3>
        <div class="grid">
            <div class="card">
                <h4>Protecting ecosystems</h4>
                <p class="small">We minimize environmental impact by choosing low-impact accommodations and local guides.</p>
            </div>
            <div class="card">
                <h4>Supporting communities</h4>
                <p class="small">Local employment and community projects are central to our itineraries.</p>
            </div>
            <div class="card">
                <h4>Responsible transport</h4>
                <p class="small">Where possible we use low-emission transport and carbon offsetting.</p>
            </div>
        </div>
    </section>
    <?php include 'includes/footer.php'; ?>

</div>
</body>
</html>
