document.addEventListener('DOMContentLoaded', () => {
    const video          = document.getElementById('topic-video');
    const watchBadge     = document.getElementById('watch-status-badge');
    const sidebarLinks   = document.querySelectorAll('.sidebar-link');
    const relatedCards   = document.querySelectorAll('.related-card');

    if (!video) return;

    const topicId   = video.dataset.id;
    const topicData = {
        id:       topicId,
        title:    video.dataset.title,
        icon:     video.dataset.icon,
        color:    video.dataset.color,
        category: video.dataset.category,
        desc:     video.dataset.desc
    };

    // ─── ADD TO RECENTLY VIEWED ───
    DSAProgress.addRecent(topicData);

    // ─── WATCH STATUS BADGE ───
    function updateWatchBadge() {
        if (!watchBadge) return;
        const watched = DSAProgress.isWatched(topicId);
        watchBadge.classList.toggle('watched', watched);
        const icon  = watchBadge.querySelector('.watch-icon');
        const label = watchBadge.querySelector('.watch-label');
        if (icon)  icon.textContent  = watched ? '✓' : '▶';
        if (label) label.textContent = watched ? 'Watched!' : 'Mark as Watched';
    }

    watchBadge?.addEventListener('click', () => {
        DSAProgress.toggleWatched(topicId);
        updateWatchBadge();
    });

    // ─── AUTO-MARK WATCHED AFTER 80% OF VIDEO ───
    video.addEventListener('timeupdate', () => {
        if (video.duration && video.currentTime / video.duration >= 0.8) {
            DSAProgress.markWatched(topicId);
            updateWatchBadge();
        }
    });

    // ─── SIDEBAR WATCHED DOTS ───
    function updateSidebarDots() {
        sidebarLinks.forEach(link => {
            const id = link.dataset.id;
            const dot = link.querySelector('.sidebar-watched-dot');
            if (dot) dot.style.display = DSAProgress.isWatched(id) ? 'inline' : 'none';
        });
    }

    // ─── RELATED CARDS WATCHED MARKS ───
    function updateRelatedMarks() {
        relatedCards.forEach(card => {
            const id   = card.dataset.id;
            const mark = card.querySelector('.related-watched-mark');
            if (mark) mark.style.display = DSAProgress.isWatched(id) ? 'flex' : 'none';
            card.classList.toggle('is-watched', DSAProgress.isWatched(id));
        });
    }

    // ─── COPY BUTTONS ───
    window.copyPseudo = function(btn) {
        const pre = document.querySelector('.pseudocode-block code');
        if (pre) {
            navigator.clipboard.writeText(pre.textContent).then(() => {
                btn.textContent = 'Copied!';
                setTimeout(() => btn.textContent = 'Copy', 2000);
            });
        }
    };

    window.copyCode = function(btn) {
        const pre = document.querySelector('.code-block.python code');
        if (pre) {
            navigator.clipboard.writeText(pre.textContent).then(() => {
                btn.textContent = 'Copied!';
                setTimeout(() => btn.textContent = 'Copy', 2000);
            });
        }
    };

    // ─── INIT ───
    updateWatchBadge();
    updateSidebarDots();
    updateRelatedMarks();
});