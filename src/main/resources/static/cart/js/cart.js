document.addEventListener("DOMContentLoaded", function() {
    fetch(`/api/cart`)
    .then(response => response.json())
    .then(data => {
        const cartItemsContainer = document.getElementById('cartItems');
        cartItemsContainer.innerHTML = data.items.map(item => `
                <div class="cart-item" data-id="${item.id}">
                    <input type="checkbox" class="item-checkbox">
                    <img src="${item.product.image1}" alt="제품 이미지" class="item-image">
                    <div class="item-details">
                        <p class="item-name">${item.product.name}</p>
                        <p class="item-price">${item.product.price}</p>
                        <p class="item-quantity">수량: <input type="number" value="${item.quantity}" class="quantity-input" min="1" onchange="updateCartItem(${item.id}, this.value)"></p>
                    </div>
                    <button class="item-remove" onclick="removeItem(${item.id})">−</button>
                </div>
            `).join('');
        updateTotalPrice();
    })
    .catch(error => {
        console.error('Error fetching cart data:', error);
    });

    document.querySelectorAll('.quantity-input').forEach(input => {
        input.addEventListener('change', updateTotalPrice);
    });

    window.onload = updateTotalPrice;
});

function goBack() {
    window.location.href = '/user/product'; // 경로 수정
}

function removeItem(itemId) {
    fetch(`/api/cart/remove?itemId=${itemId}`, { method: 'DELETE' })
    .then(response => response.json())
    .then(data => {
        // Remove the item from the DOM
        document.querySelector(`.cart-item[data-id='${itemId}']`).remove();
        // Update the total price
        updateTotalPrice();
    })
    .catch(error => {
        console.error('Error removing cart item:', error);
    });
}

function clearCart() {
    fetch(`/api/cart/clear`, { method: 'DELETE' })
    .then(response => {
        if (response.ok) {
            document.getElementById('cartItems').innerHTML = '';
            updateTotalPrice();
        } else {
            console.error('Error clearing cart:', response);
        }
    })
    .catch(error => {
        console.error('Error clearing cart:', error);
    });
}

function updateCartItem(itemId, quantity) {
    fetch(`/api/cart/update?itemId=${itemId}&quantity=${quantity}`, { method: 'PUT' })
    .then(response => response.json())
    .then(data => {
        updateTotalPrice();
    })
    .catch(error => {
        console.error('Error updating cart item:', error);
    });
}

function updateTotalPrice() {
    let total = 0;
    document.querySelectorAll('.cart-item').forEach(item => {
        const price = parseFloat(item.querySelector('.item-price').innerText.replace(',', ''));
        const quantity = parseInt(item.querySelector('.quantity-input').value);
        total += price * quantity;
    });
    document.getElementById('totalPrice').innerText = total.toLocaleString() + '원';
}
