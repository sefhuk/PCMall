document.addEventListener("DOMContentLoaded", function() {
    fetch(`/api/cart`)
    .then(response => response.json())
    .then(data => {
        const cartItemsContainer = document.getElementById('cartItems');
        cartItemsContainer.innerHTML = data.items.map(item => `
                <div class="cart-item">
                    <input type="checkbox" class="item-checkbox">
                    <img src="${item.product.image1}" alt="제품 이미지" class="item-image">
                    <div class="item-details">
                        <p class="item-name">${item.product.name}</p>
                        <p class="item-price">${item.product.price}</p>
                        <p class="item-quantity">수량: <input type="number" value="${item.quantity}" class="quantity-input" min="1"></p>
                    </div>
                    <button class="item-remove" onclick="removeItem(${item.id})">−</button>
                </div>
            `).join('');
    })
    .catch(error => {
        console.error('Error fetching cart data:', error);
    });
});

function goBack() {
    window.history.back();
}

function removeItem(itemId) {
    // 아이템을 제거하는 로직
    alert('아이템 ' + itemId + ' 제거');
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

document.querySelectorAll('.quantity-input').forEach(input => {
    input.addEventListener('change', updateTotalPrice);
});

window.onload = updateTotalPrice;
