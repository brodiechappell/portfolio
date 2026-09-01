<?php
/*
This code is distributed under Creative Commons Attribution 4.0 International
https://creativecommons.org/licenses/by/4.0/

You are free to:
- Share - copy and redistribute the material in any medium or format
- Adapt - remix, transform, and build upon the material for any purpose, even commercially.

Under the following terms:
- Attribution - You must give appropriate credit, provide a link to the license, and indicate
if changes were made. You may do so in any reasonable manner, but not in any way that suggests
the licensor endorses you or your use.

The licensor cannot revoke these freedoms as long as you follow the license terms.
No additional restrictions - You may not apply legal terms or technological measures that legally
restrict others from doing anything the license permits.

The suggested attribution is:
Based on Code by Mark Dunlop from University of Strathclyde, Scotland under
Creative Commons licence. Source https://personal.cis.strath.ac.uk/mark.dunlop/teaching/
*/
/**
* Created by IntelliJ IDEA.
* User: BRODIE CHAPPELL
* Date: 05/11/25
*/
?>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Template PHP MySQL Select*</title>
</head>
<body>
<div>

    <?php
    //Connect to MySQL
    $host = "devweb2025.cis.strath.ac.uk";//set year for devweb
    $user = "jrb23167";//your username
    $pass = "to0vahmahJ2a";//your MySQL password
    $dbname = $user;
    $conn = new mysqli($host, $user, $pass, $dbname);


    //Issue the query
    $sql = "SELECT * FROM `orders`";//note do not have `abc01234`. in front of the table name if you used $dbname to connect
    $result = $conn->query($sql);


    //Handle the results


    //Disconnect
    ?>

</div>
</body>
</html>