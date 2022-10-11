// This file contains helper functions for manipulating the user's cart


// This is a helper function to add items to the shopping cart
export function addItemToCart(item){
    // Get our current cart items
    const userCart = localStorage.getItem("cart");
    const cartItems = JSON.parse(userCart);

    // Set the item that the user selected
    const newItem = {"itemID": item.itemID, "quantity": 1};

    // Check if the user has a cart
    if(userCart){
        let cart = [];
        let itemAlreadyInCart = false;

        // Check if the user is adding a copy of an item that is already in the cart, in this case, increment the quantity
        cartItems.forEach((cartItem) => {
            if(cartItem.itemID === item.itemID){
                cartItem.quantity += 1;
                itemAlreadyInCart = true;
            }
            // Add items into the new cart array as we cannot directly update cartItems since it is an arraylist of json objects
            cart.push(cartItem);
        });

        // If the item is not already in the cart, add it to the cart with quantity of 1
        if(!itemAlreadyInCart){
            cart.push(newItem);
        }

        // Finally, save the new cart in local storage
        localStorage.setItem("cart", JSON.stringify(cart));
    }else{
        // When card is empty or doesn't exist, create a new one and add the item to cart
        let cartList = [];
        cartList.push(newItem);
        localStorage.setItem("cart", JSON.stringify(cartList));
    }
}


// This function removes an item from cart, takes a parameter of the item to be removed
export function removeItemFromCart(item){
    // Get our current cart items
    let cart = [];
    const userCart = localStorage.getItem("cart");
    const cartItems = JSON.parse(userCart);

    // Set the item that the user selected
    const itemSelected = {"itemID": item.itemID, "quantity": 1};

    // Search for the item then remove 1 quantity when there is more than 1 quantity of it
    // if there is only 1 quantity we simply do not add it to the cart array
    cartItems.forEach((cartItem) => {
        if ((cartItem.itemID === itemSelected.itemID) && (cartItem.quantity > 1)){
            cartItem.quantity -= 1;
            cart.push(cartItem);
        }

        // Add the other items to the cart array as they are not effected
        if (cartItem.itemID !== itemSelected.itemID){
            cart.push(cartItem);
        }
    });
    // Save the new cart in local storage
    localStorage.setItem("cart", JSON.stringify(cart));
}


// Remove all copies of an item from cart
export function removeItemsFromCart(item){
    // Get our current cart items
    let cart = [];
    const userCart = localStorage.getItem("cart");
    const cartItems = JSON.parse(userCart);

    // Add the other items that the user didn't want to remove to the cart array
    cartItems.forEach((cartItem) => {
        if (cartItem.itemID !== item){
            cart.push(cartItem);
        }
    });
    localStorage.setItem("cart", JSON.stringify(cart));
}


// Remove all items from cart
export function removeAllItemsFromCart(){
    localStorage.removeItem("cart");
}