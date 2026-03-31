/**
 * DSA Progress Tracker — localStorage-based
 * Used on both index.html and topic.html
 */
const DSAProgress = (() => {
    const STORAGE_KEY = 'dsa_watched';
    const RECENT_KEY  = 'dsa_recent';
    const MAX_RECENT  = 8;

    function getWatched() {
        try { return JSON.parse(localStorage.getItem(STORAGE_KEY)) || []; }
        catch { return []; }
    }

    function getRecent() {
        try { return JSON.parse(localStorage.getItem(RECENT_KEY)) || []; }
        catch { return []; }
    }

    function isWatched(id) { return getWatched().includes(id); }

    function markWatched(id) {
        const watched = getWatched();
        if (!watched.includes(id)) {
            watched.push(id);
            localStorage.setItem(STORAGE_KEY, JSON.stringify(watched));
        }
        return watched;
    }

    function unmarkWatched(id) {
        const watched = getWatched().filter(w => w !== id);
        localStorage.setItem(STORAGE_KEY, JSON.stringify(watched));
        return watched;
    }

    function toggleWatched(id) {
        return isWatched(id) ? unmarkWatched(id) : markWatched(id);
    }

    function addRecent(topicData) {
        // topicData: { id, title, icon, color, category, desc }
        let recent = getRecent().filter(r => r.id !== topicData.id);
        recent.unshift(topicData);
        if (recent.length > MAX_RECENT) recent = recent.slice(0, MAX_RECENT);
        localStorage.setItem(RECENT_KEY, JSON.stringify(recent));
    }

    function clearRecent() { localStorage.removeItem(RECENT_KEY); }

    function getProgress(total) {
        const count = getWatched().length;
        return { count, total, pct: total > 0 ? Math.round((count / total) * 100) : 0 };
    }

    return { getWatched, getRecent, isWatched, markWatched, unmarkWatched, toggleWatched, addRecent, clearRecent, getProgress };
})();