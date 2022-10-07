<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
    <link rel="stylesheet" href="style.css">
    <script src="script.js"></script>
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
  print "<li><a href=login.php>Login</a></li>";
  print  "<li><a href=register.php>Register</a></li>";

}
else {
echo "Welcome back ". $_SESSION["username"];
print "<li><a class=activehref=index.php>Home</a></li>";
print "<li><a href=shop.php>Shop</a></li>";
print  "<li><a href=logout.php>Logout</a></li>";

}
?>
 <li><a class='active' href="cart.php"><i class="fa-solid fa-cart-shopping"></i></a></li>
</ul>
</div>
</section>

<section id="page-header">
    <h2>Cart Page</h2>
    <p>Welcome to Page</p>

   
</section>

<section id="cart" class="section-p1">
    <table width="100%">
        <thead>
         <tr>
             <td>Remove</td>
             <td>Image</td>
             <td>Product</td>
             <td>Price</td>
             <td>Quantity</td>
             <td>Subtotal</td>
         </tr>
        </thead>
            <tbody>
                <tr>
                    <td><a href="#"><i class="fa-solid fa-x"></i></a></td>
                    <td> <img src="tut/img/products/f1.jpg"></td>
                    <td>Nike SB</td>
                    <td>$118.19</td>
                    <td><input type="number" value="1"></td>
                    <td>$118.19</td>
                </tr>
            </tbody>
    </table>
</section>

<section id="cart-add" class="section-p1">
    <div id="coupon">
        <h3>Apply Coupon</h3>
        <div>
            <input type="text" placeholder="Enter Your Coupon">
            <button class="normal">Apply</button>
        </div>
    </div>
    <div id="subtotal">
        <h3>Cart Totals</h3>
        <table>
            <tr>
                <td>Cart Subtotal</td>
                <td>$ 335</td>
            </tr>
            <tr>
                <td>Shipping</td>
                <td>Free</td>
            </tr>
            <tr>
                <td><strong>Total</strong></td>
                <td>$ 335</td>
            </tr>
            <div>
                <script src="googlePay.js"></script>
				<script async src="https://pay.google.com/gp/p/js/pay.js" onload="onGooglePayLoaded()"></script>
    
            </div>
        </table>

        <button id="buy-now" name="buy-now"></button>
    </div>
</section>
</body>

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

</footer>
</html>