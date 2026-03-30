document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('search-input');
    const filterBtns = document.querySelectorAll('.filter-btn');
    const cards = document.querySelectorAll('.topic-card');
    const modalOverlay = document.getElementById('modal-overlay');
    const modalVideo = document.getElementById('modal-video');
    const modalTitle = document.getElementById('modal-title');
    const modalDesc = document.getElementById('modal-desc');
    const modalTags = document.getElementById('modal-tags');
    const modalClose = document.getElementById('modal-close');
    const sections = document.querySelectorAll('.category-section');

    let activeFilter = 'all';
    let searchQuery = '';

    // ─── STAGGER ANIMATION ───
    cards.forEach((card, i) => {
        card.style.animationDelay = `${i * 0.04}s`;
    });

    // ─── FILTER ───
    filterBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            filterBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            activeFilter = btn.dataset.filter;
            applyFilters();
        });
    });

    // ─── SEARCH ───
    searchInput.addEventListener('input', (e) => {
        searchQuery = e.target.value.toLowerCase().trim();
        applyFilters();
    });

    function applyFilters() {
        let visibleCount = 0;

        cards.forEach(card => {
            const title = card.dataset.title.toLowerCase();
            const category = card.dataset.category.toLowerCase();
            const tags = card.dataset.tags.toLowerCase();

            const matchesSearch = !searchQuery ||
                title.includes(searchQuery) ||
                category.includes(searchQuery) ||
                tags.includes(searchQuery);

            const matchesFilter = activeFilter === 'all' ||
                category === activeFilter.toLowerCase();

            if (matchesSearch && matchesFilter) {
                card.style.display = '';
                visibleCount++;
            } else {
                card.style.display = 'none';
            }
        });

        // Show/hide category sections
        sections.forEach(section => {
            const visibleCards = section.querySelectorAll('.topic-card:not([style*="display: none"])');
            section.style.display = visibleCards.length === 0 ? 'none' : '';
        });

        // Update count
        const countEl = document.getElementById('topic-count');
        if (countEl) countEl.textContent = visibleCount;
    }

    // ─── MODAL ───
    cards.forEach(card => {
        card.addEventListener('click', (e) => {
            e.preventDefault();
            const title = card.dataset.title;
            const desc = card.dataset.desc;
            const video = card.dataset.video;
            const tags = card.dataset.tags;

            modalTitle.textContent = title;
            modalDesc.textContent = desc;
            modalTags.innerHTML = tags.split(',').map(t =>
                `<span class="tag">${t.trim()}</span>`
            ).join('');

            // Set video source
            if (video) {
                modalVideo.src = `/videos/${video}`;
                modalVideo.load();
            } else {
                modalVideo.src = '';
            }

            modalOverlay.classList.add('open');
            document.body.style.overflow = 'hidden';
        });
    });

    function closeModal() {
        modalOverlay.classList.remove('open');
        document.body.style.overflow = '';
        modalVideo.pause();
        modalVideo.src = '';
    }

    modalClose.addEventListener('click', closeModal);
    modalOverlay.addEventListener('click', (e) => {
        if (e.target === modalOverlay) closeModal();
    });

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeModal();
    });
});
