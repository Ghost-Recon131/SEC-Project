<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <link rel="stylesheet" href="style.css">
  <script src="https://kit.fontawesome.com/999f694d3f.js" crossorigin="anonymous"></script>

</head>
<body>
<section id="header">
  <a href="#"><img src="tut/img/logo.png" class="logo"></a>
  <div>

<ul id="navbar">


  
    <?php
session_start();
if(!isset($_SESSION['username'])) {


  print "<li><a href=index.php>Home</a></li>";
  print "<li><a href=shop.php>Shop</a></li>";
  print "<li><a class=active href=login.php>Login</a></li>";
  print  "<li><a href=register.php>Register</a></li>";

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
  <h2>Login Page</h2>



</section>

<section id="login-form" >
  <div class="wrapper">
    <h1>Hello Again!</h1>
    <p>Welcome back you've <br> been missed!</p>
    <form method="post" action="process_login.php">
      <input type="text" placeholder="Enter username" name="username">
      <input type="password" placeholder="Password" name="password" id="password">
      <p class="recover">
        <a href="#">Recover Password</a>
      </p>
        <button type="submit">Sign in</button>
    </form>

    <p class="or">
      ----- or continue with -----
    </p>
    <div class="icons">
      <i class="fab fa-google"></i>
      <i class="fab fa-github"></i>
      <i class="fab fa-facebook"></i>
    </div>
    <div class="not-member">
      Not a member? <a href="register.php">Register Now</a>
    </div>
  </div>
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


</script>

</footer>
</body>
</html>