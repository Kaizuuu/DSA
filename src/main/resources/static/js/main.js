document.addEventListener('DOMContentLoaded', () => {
    const searchInput   = document.getElementById('search-input');
    const filterBtns    = document.querySelectorAll('.filter-btn');
    const cards         = document.querySelectorAll('.topic-card');
    const sections      = document.querySelectorAll('.category-section');
    const totalTopics   = cards.length;

    let activeFilter = 'all';
    let searchQuery  = '';

    // ─── STAGGER ANIMATION ───
    cards.forEach((card, i) => {
        card.style.animationDelay = `${i * 0.045}s`;
    });

    // ─── PROGRESS BAR ───
    function updateProgressUI() {
        const prog = DSAProgress.getProgress(totalTopics);
        const statEl  = document.getElementById('progress-stat');
        const fillEl  = document.getElementById('progress-track-fill');
        const labelEl = document.getElementById('progress-track-label');
        if (statEl)  statEl.textContent = prog.pct + '%';
        if (fillEl)  fillEl.style.width = prog.pct + '%';
        if (labelEl) {
            const countSpan = labelEl.querySelector('span') || document.createElement('span');
            labelEl.innerHTML = `${prog.count} / <span>${prog.total}</span> topics watched`;
        }
    }

    // ─── WATCHED BADGES ON CARDS ───
    function applyWatchedBadges() {
        cards.forEach(card => {
            const id = card.dataset.id;
            const badge = card.querySelector('.watched-badge');
            if (badge) {
                badge.style.display = DSAProgress.isWatched(id) ? 'flex' : 'none';
            }
            if (DSAProgress.isWatched(id)) {
                card.classList.add('is-watched');
            } else {
                card.classList.remove('is-watched');
            }
        });
    }

    // ─── RECENTLY VIEWED ROW ───
    function renderRecentRow() {
        const section  = document.getElementById('recently-viewed-section');
        const row      = document.getElementById('recent-cards-row');
        const recent   = DSAProgress.getRecent();

        if (!section || !row) return;

        if (recent.length === 0) {
            section.style.display = 'none';
            return;
        }

        section.style.display = '';
        row.innerHTML = recent.map(t => `
            <a href="/topic/${t.id}" class="recent-mini-card ${DSAProgress.isWatched(t.id) ? 'is-watched' : ''}"
               data-id="${t.id}">
                <div class="recent-thumb" style="background: linear-gradient(135deg, ${t.color}, ${t.color}bb)">
                    <span>${t.icon}</span>
                </div>
                <div class="recent-info">
                    <div class="recent-title">${t.title}</div>
                    <div class="recent-cat">${t.category}</div>
                </div>
                ${DSAProgress.isWatched(t.id) ? '<div class="recent-watched-mark">✓</div>' : ''}
            </a>
        `).join('');
    }

    // ─── CLEAR RECENT ───
    const clearBtn = document.getElementById('clear-recent');
    if (clearBtn) {
        clearBtn.addEventListener('click', () => {
            DSAProgress.clearRecent();
            renderRecentRow();
        });
    }

    // ─── TRACK CARD CLICKS → ADD TO RECENT ───
    cards.forEach(card => {
        card.addEventListener('click', () => {
            const topicData = {
                id:       card.dataset.id,
                title:    card.dataset.title,
                icon:     card.dataset.icon,
                color:    card.dataset.color,
                category: card.dataset.category,
                desc:     card.dataset.desc
            };
            DSAProgress.addRecent(topicData);
        });
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
    if (searchInput) {
        // Pre-fill from URL param
        const params = new URLSearchParams(window.location.search);
        const q = params.get('q');
        if (q) { searchInput.value = q; searchQuery = q.toLowerCase().trim(); }

        searchInput.addEventListener('input', e => {
            searchQuery = e.target.value.toLowerCase().trim();
            applyFilters();
        });
    }

    function applyFilters() {
        let visibleCount = 0;

        cards.forEach(card => {
            const title    = card.dataset.title.toLowerCase();
            const category = card.dataset.category.toLowerCase();
            const tags     = card.dataset.tags.toLowerCase();

            const matchesSearch = !searchQuery ||
                title.includes(searchQuery) || category.includes(searchQuery) || tags.includes(searchQuery);

            const matchesFilter = activeFilter === 'all' ||
                card.dataset.category === activeFilter;

            if (matchesSearch && matchesFilter) {
                card.style.display = '';
                visibleCount++;
            } else {
                card.style.display = 'none';
            }
        });

        sections.forEach(section => {
            const visible = section.querySelectorAll('.topic-card:not([style*="display: none"])');
            section.style.display = visible.length === 0 ? 'none' : '';
        });

        const countEl = document.getElementById('topic-count');
        if (countEl) countEl.textContent = visibleCount;
    }

    // ─── INIT ───
    applyWatchedBadges();
    updateProgressUI();
    renderRecentRow();
    applyFilters();
});