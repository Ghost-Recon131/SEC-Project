<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product</title>
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

<section id="prodetails" class="section-p1">
    <div class="single-pro-image">
        <img src="tut/img/products/f1.jpg" width="100%" id="MainImg">

        <div class="small-img-group">
            <div class="small-img-col">
                <img src="tut/img/products/f1.jpg" width="100%" class="small-img">
            </div>
            <div class="small-img-col">
                <img src="tut/img/products/f2.jpg" width="100%" class="small-img">
            </div>
            <div class="small-img-col">
                <img src="tut/img/products/f3.jpg" width="100%" class="small-img">
            </div>
            <div class="small-img-col">
                <img src="tut/img/products/f4.jpg" width="100%" class="small-img">
            </div>
        </div>
    </div>

    <div class="single-pro-details">
        <h6>Home / T-shirt</h6>
        <h4>Men's Fashion T Shirt</h4>
        <h2>$139.00</h2>
        <select>
            <option>Select Size</option>
            <option>XXL</option>
            <option>XL</option>
            <option>Large</option>
            <option>Small</option>
        </select>
        <input type="number"value="1">
        <button class="normal">Add to Cart</button>
        <h4>Product Details</h4>
        <span>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean lacus massa, egestas eu sem non, molestie consequat metus. In hac habitasse platea dictumst. Curabitur nisl magna, pharetra sit amet ex quis, dictum aliquet neque. Mauris eget sodales sapien, sit amet interdum diam. Nulla accumsan lobortis nibh in sagittis. Praesent magna libero, hendrerit in felis id, molestie finibus justo. Pellentesque enim turpis, tempus vitae molestie eu, ullamcorper non urna. Nullam faucibus feugiat massa, ut dapibus justo viverra quis. Vestibulum at congue quam.</span>
    </div>
</section>

<section id="product1" class="section-p1">
    <h2>Feature Products</h2>
    <p>Summer collection New Modern Design</p>
    <div class ="pro-container">
        <div class="pro">
            <img src="tut/img/products/n1.jpg">
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
            <a href="#"><i class="fa-solid fa-cart-plus cart"></i></a>
        </div>

        <div class="pro">
            <img src="tut/img/products/n2.jpg">
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
            <img src="tut/img/products/n3.jpg">
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
            <img src="tut/img/products/n4.jpg">
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

    </div>

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

<script>
    var MainImg = document.getElementById("MainImg");
    var smallimg = document.getElementsByClassName("small-img")

    smallimg[0].onclick = function (){
        MainImg.src = smallimg[0].src;
    }
    smallimg[1].onclick = function (){
        MainImg.src = smallimg[1].src;
    }
    smallimg[2].onclick = function (){
        MainImg.src = smallimg[2].src;
    }
    smallimg[3].onclick = function (){
        MainImg.src = smallimg[3].src;
    }
</script>
</body>
</html>