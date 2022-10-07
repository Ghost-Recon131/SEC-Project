<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop</title>
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
  print "<li><a class=active href=shop.php>Shop</a></li>";
  print "<li><a href=login.php>Login</a></li>";
  print  "<li><a href=register.php>Register</a></li>";

}
else {
echo "<h4>Welcome back. $_SESSION[username]</h4>";
print "<li><a href=index.php>Home</a></li>";
print "<li><a class=active href=shop.php>Shop</a></li>";
print  "<li><a href=logout.php>Logout</a></li>";

}
?>
<li><a href="cart.php"><i class="fa-solid fa-cart-shopping"></i></a></li>
</ul>
</div>
</section>

<section id="page-header">
    <h2>Shop Page</h2>
    <p>Welcome to the shop page</p>


</section>

<section id="product1" class="section-p1">
    <div class ="pro-container">
        <div class="pro" onclick="window.location.href='sproduct.php';">
            <img src="tut/img/products/f1.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 1</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-bag-shopping cart"></i></a>
            <i class=""></i>

        </div>

        <div class="pro">
            <img src="tut/img/products/f2.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 2</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/f3.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 3</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/f4.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 4</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/f5.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 5</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/f6.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 6</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/f7.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 7 </h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>
        <div class="pro">
            <img src="tut/img/products/f8.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 8</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n1.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Product 9</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n2.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Jordan 1 Retro High Off-White University Blue</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n3.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Jordan 1 High OG SP Fragment x Travis Scott</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n4.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Jordan 1 Retro High Not for Resale Varsity Maize</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n5.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Jordan 1 Retro High Travis Scott</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n6.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Jordan 1 Retro High Rookie of the Year</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n7.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Nike Air Force 1 Low Off-White Brooklyn</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>
        <div class="pro">
            <img src="tut/img/products/n8.jpg">
            <div class="des">
                <span>Nike</span>
                <h5>Nike Air Force 1 Low Off-White MCA University Blue</h5>
                <div class="star">
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                    <i class="fas fa-star"></i>
                </div>
                <h4>$220</h4>
            </div>
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>


    </div>

</section>

<section id="pagination" class="section-p1">
    <a href="#">1</a>
    <a href="#">2</a>
    <a href="#">  <i class="fa-solid fa-arrow-right"></i></a>


</section>

<section id="newsletter" class="section-p1 section-m1">
    <div class="newstext">
        <h4>Sign Up For Newsletter</h4>
        <p>Get E-mail updates about our latest shop and <span>special offers.</span></p>
    </div>
    <div class="form">
        <input type="text" placeholder="Your Email Address">
        <button class="normal">Sign Up</button>
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

</footer>
</body>
</html>