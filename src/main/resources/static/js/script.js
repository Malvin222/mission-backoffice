// 샘플 데이터
const brands = ['Nike', 'Adidas', 'Puma'];
const categories = ['의류', '신발', '액세서리'];
const products = [
    { name: 'Air Max', category: '신발', brand: 'Nike' },
    { name: 'UltraBoost', category: '신발', brand: 'Adidas' },
];
const stocks = [
    { product: 'Air Max', count: 10, price: 200000 },
    { product: 'UltraBoost', count: 5, price: 250000 },
];

// 섹션 보이기
function showSection(sectionId) {
    document.querySelectorAll('.section').forEach((section) => {
        section.classList.add('hidden');
    });
    document.getElementById(sectionId).classList.remove('hidden');
}

// 데이터 렌더링
function renderData() {
    const brandList = document.getElementById('brand-list');
    brands.forEach((brand) => {
        const li = document.createElement('li');
        li.textContent = brand;
        brandList.appendChild(li);
    });

    const categoryList = document.getElementById('category-list');
    categories.forEach((category) => {
        const li = document.createElement('li');
        li.textContent = category;
        categoryList.appendChild(li);
    });

    const productList = document.getElementById('product-list');
    products.forEach((product) => {
        const li = document.createElement('li');
        li.textContent = `${product.name} - ${product.category} (${product.brand})`;
        productList.appendChild(li);
    });

    const stockList = document.getElementById('stock-list');
    stocks.forEach((stock) => {
        const li = document.createElement('li');
        li.textContent = `${stock.product}: ${stock.count}개, ${stock.price}원`;
        stockList.appendChild(li);
    });
}

// 초기화
window.onload = renderData;
