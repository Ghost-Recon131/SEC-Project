<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="style.css">
    <script src="https://kit.fontawesome.com/999f694d3f.js" crossorigin="anonymous"></script>
    <script src="sha256.js"></script>
    

</head>
<body>
<section id="header">
    <a href="#"><img src="tut/img/logo.png" class="logo"></a>
    <div>

<ul id="navbar">


  
    <?php
session_start();
if(!isset($_SESSION['username'])) {


  print "<li><a  href=index.php>Home</a></li>";
  print "<li><a href=shop.php>Shop</a></li>";
  print "<li><a href=login.php>Login</a></li>";
  print  "<li><a class=active href=register.php>Register</a></li>";

}
else {
echo "<h4>Welcome back. $_SESSION[username]</h4>";
print "<li><a href=index.php>Home</a></li>";
print "<li><a href=shop.php>Shop</a></li>";
print  "<li><a href=logout.php>Logout</a></li>";

}
?>
<li><a href="cart.php"><i class="fa-solid fa-cart-shopping"></i></a></li>
</ul>
</div>
</section>

<section id="page-header">
    <h2>Register Page</h2>



</section>

<section id="reg-form" >
    <div class="signup-box">

    <h1>Sign Up</h1>
    <h4>It's free and only takes a minute</h4>
    <form class="form" id="form" action="process_register.php" method="post" >
        <label>Username</label>
        <input type="text" placeholder="Enter Username" id="username" name="username" required="required" >

        <label>E-mail</label>
        <input type="text" placeholder="Enter E-mail" id="email" name="email" required="required" >

        <label>First Name</label>
        <input type="text" placeholder="Enter First Name" id="firstname" name="firstname" required="required" >

        <label>Last Name</label>
        <input type="text" placeholder="Enter Last Name" id="lastname" name="lastname" required="required" >

        <label>Password</label>
        <input type="password" placeholder="Enter Password" id="password" name="password" required="required" >

        <label>Confirm Password</label>
        <input type="password" placeholder="Confirm Password"  required="required" >

        <label>Secret Question</label>
        <input type="text" placeholder="Enter Secret Question" id="secret_question" name="secret_question" required="required" >

        <label>Secret Question Answer</label>
        <input type="text" placeholder="Enter Secret Answer" id="secret_question_answer" name="secret_question_answer" required="required" >
       
        
        <input type="submit" value="Submit" id="submit" onclick="hashPassword()">


    </form>
        <p class="terms">By Clicking the Sign Up button,you agree to our <br>
         <a href="#">Terms and Conditions</a> and <a href="#">Policy Privacy</a>

        </p>
    </div>
        <p class="login-para">Already have an account? <a href="login.php">Login here</a></p>

</section>

<footer class="section-p1">
    <div class="col">
        <img src="tut/img/logo.png" class="logo">
        <h4>Contact</h4>
        <p><strong>Address: </strong> 123 Smith Street, Ascot Vale, 3032, Australia</p>
        <p><strong>Phone: </strong> +61 456454768</p>
        <p><strong>Hours: </strong> 10:00 - 20:00, Mon - Sat </p>
        <div class="follow">
            <h4>Follow Us</h4>
            <div class="icon">
                <i class="fab fa-facebook-f"></i>
                <i class="fab fa-twitter"></i>
                <i class="fab fa-instagram"></i>
                <i class="fab fa-pinterest-p"></i>
                <i class="fab fa-youtube"></i>


            </div>
        </div>
    </div>

    <div class="col">
        <h4>About</h4>
        <a href="#">About Us</a>
        <a href="#">Delivery Information</a>
        <a href="#">Privacy Policy</a>
        <a href="#">Terms and Conditions</a>
        <a href="#">Contact Us</a>
    </div>

    <div class="col">
        <h4>My Account</h4>
        <a href="#">Sign in</a>
        <a href="#">View Cart</a>
        <a href="#">My Wishlist</a>
        <a href="#">Track My Order</a>
        <a href="#">Help</a>
    </div>

    <div class="col install">
        <h4>Install App</h4>
        <p>From App Store or Google Play</p>
        <div class="row">
            <img src="tut/img/pay/app.jpg" alt="">
            <img src="tut/img/pay/play.jpg" alt="">
        </div>
        <p>Secured Payment Gateways</p>
        <img src="tut/img/pay/pay.png" alt="">
    </div>

    <div class="copyright">
        <p>&copy; 2022, The Boys </p>
    </div>
<script>
		function hashPassword() {
			var input = document.getElementById('password').value;
			
			var hash = SHA256.hash(input);
			
			document.getElementById("password").innerHTML = hash;
			document.getElementById("password").value = hash;
		}
    

</script>


</footer>
</body>
</html>