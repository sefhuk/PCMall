document.addEventListener("DOMContentLoaded", function() {
    loadCartItems();

    document.getElementById('clearCartBtn').addEventListener('click', clearCart);
});

function loadCartItems() {
    fetch(`/api/cart`)
    .then(response => response.json())
    .then(data => {
        if (!data || !data.items) {
            throw new Error("Invalid cart data received");
        }
        const cartItemsContainer = document.getElementById('cartItems');
        cartItemsContainer.innerHTML = data.items.map(item => `
            <div class="cart-item" data-id="${item.id}">
                <input type="checkbox" class="item-checkbox" onchange="updateTotalPrice()">
                <img src="${item.product.image1}" alt="제품 이미지" class="item-image">
                <div class="item-details">
                    <p class="item-name">${item.product.name}</p>
                    <p class="item-price">가격: ${item.product.price.toFixed(1)}원</p>
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
}

function goBack() {
    window.location.href = '/user/product';
}

function removeItem(itemId) {
    fetch(`/api/cart/remove?itemId=${itemId}`, { method: 'DELETE' })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const itemElement = document.querySelector(`.cart-item[data-id='${itemId}']`);
        if (itemElement) {
            itemElement.remove();
        }
        updateTotalPrice();
    })
    .catch(error => {
        alert('Error removing cart item: ' + error.message);
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
        const checkbox = item.querySelector('.item-checkbox');
        if (checkbox.checked) {
            const price = parseFloat(item.querySelector('.item-price').innerText.replace('가격: ', '').replace('원', '').replace(',', ''));
            const quantity = parseInt(item.querySelector('.quantity-input').value);
            total += price * quantity;
        }
    });
    document.getElementById('totalPrice').innerText = total.toLocaleString() + '원';
}

function toOrder() {
    const selectedItems = Array.from(document.querySelectorAll('.item-checkbox:checked'));
    if (selectedItems.length === 0) {
        alert('주문할 상품을 선택해주세요.');
        return;
    }

    const productIds = [];
    const counts = [];

    selectedItems.forEach(checkbox => {
        const productId = checkbox.getAttribute('data-product-id');
        const quantity = checkbox.closest('.cart-item').querySelector('.quantity-input').value;
        productIds.push(productId);
        counts.push(quantity);
    });

    const productIdsStr = productIds.join(',');
    const quantitiesStr = counts.join(',');

    const url = '/user/order/sheet?productIds=' + productIdsStr + '&counts=' + quantitiesStr;

    window.location.href = url;
}

function toggleSelectAll() {
    const selectAllCheckbox = document.getElementById('selectAll');
    const itemCheckboxes = document.querySelectorAll('.item-checkbox');
    itemCheckboxes.forEach(checkbox => {
        checkbox.checked = selectAllCheckbox.checked;
    });
    updateTotalPrice();
}
