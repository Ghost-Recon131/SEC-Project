<?php
 $firstname = $_POST['firstname'];
 $lastname  = $_POST['lastname'];
 $username  = $_POST['username'];
 $email     = $_POST['email'];
 $password  = $_POST['password'];
 //connect to the server 
 $db = mysqli_connect("localhost", "root", "","ecommerce" );


$q = "insert into users values(null, '$firstname',$lastname,$username,$email,$password, now())";
mysqli_query($db, $q);






header("Location:index.php");

?>


firstname varchar(100),
    lastname varchar(100),
	username varchar(100),
    email varchar(255),
	password char(100),