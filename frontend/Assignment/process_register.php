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

 $q1 = "select * from users where username='$username'";
 $results = mysqli_query($db, $q1) or die(mysqli_error($db));

 if(mysqli_num_rows($results) > 0)
    {
            
        header("Location:register.php");
        
    }
    else {
        $q = "insert into users values(null, '$username' ,'$email','$firstname','$lastname','$password','$secret_question','$secret_question_answer', now())";
        mysqli_query($db, $q);
        header("Location:index.php");
    }








?>
