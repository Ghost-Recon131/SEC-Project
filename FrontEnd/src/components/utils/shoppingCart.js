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

        // Update the number of items in cart
        let existingCount = parseInt(localStorage.getItem("cartQuantity"));
        if(existingCount){
            existingCount += 1;
        }else{
            existingCount = 1;
        }
        localStorage.setItem("cartQuantity", existingCount.toString());
    }else{
        // When card is empty or doesn't exist, create a new one and add the item to cart
        let cartList = [];
        cartList.push(newItem);

        // Generate cart quantity counter
        localStorage.setItem("cart", JSON.stringify(cartList));
        localStorage.setItem("cartQuantity", "1")
    }
}

// Remove all copies of an item from cart
export function removeItemsFromCart(item){
    // Get our current cart items
    let cart = [];
    const userCart = localStorage.getItem("cart");
    const cartItems = JSON.parse(userCart);

    // Add the other items that the user didn't want to remove to the cart array
    let amountRemoved = 0;
    cartItems.forEach((cartItem) => {
        if(cartItem.itemID !== item){
            cart.push(cartItem);
        }

        // Get the number of items removed
        if(cartItem.itemID === item){
            amountRemoved = cartItem.quantity;
        }
    });
    // Save the new cart without the item removed
    localStorage.setItem("cart", JSON.stringify(cart));

    // Update the current count
    let existingCount = parseInt(localStorage.getItem("cartQuantity"));
    if(existingCount){
        existingCount = existingCount - amountRemoved;
    }
    localStorage.setItem("cartQuantity", existingCount.toString());
}


// Remove all items from cart
export function removeAllItemsFromCart(){
    localStorage.removeItem("cart");
}