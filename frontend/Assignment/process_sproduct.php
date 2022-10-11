<?php

session_start();

if(empty($_SESSION['single'])){
    $_SESSION['single'] = array();
}

array_push($_SESSION['single'],$_GET['id']);

header("Location:sproduct.php");
?>