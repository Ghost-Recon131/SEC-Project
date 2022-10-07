<?php
 $username  = $_POST['username'];
 $email     = $_POST['email'];
 $firstname = $_POST['firstname'];
 $lastname  = $_POST['lastname'];
 $password  = $_POST['password'];
 $secret_question = $_POST['secret_question'];
 $secret_question_answer = $_POST['secret_question_answer'];
 //connect to the server 
 $db = mysqli_connect("localhost", "root", "","ecommerce" );


$q = "insert into users values(null, '$username' ,'$email','$firstname','$lastname',SHA('$password'),'$secret_question','$secret_question_answer', now())";
mysqli_query($db, $q);






header("Location:index.php");

?>
