/**
 * DSA Interactive Visualizers
 * Renders into div#viz-{topicId} on the topic page
 */
document.addEventListener('DOMContentLoaded', () => {
    const containers = document.querySelectorAll('[id^="viz-"]');
    containers.forEach(container => {
        const id = container.id.replace('viz-', '');
        if (VISUALIZERS[id]) VISUALIZERS[id](container);
    });
});

/* ═══════════════════════════════════════
   SHARED HELPERS
═══════════════════════════════════════ */
function makeArray(size = 10, min = 5, max = 95) {
    const arr = [];
    for (let i = 0; i < size; i++) arr.push(Math.floor(Math.random() * (max - min)) + min);
    return arr;
}

function sleep(ms) { return new Promise(r => setTimeout(r, ms)); }

function buildVizShell(container, title, extraControls = '') {
    container.innerHTML = `
        <div class="viz-shell">
            <div class="viz-header">
                <span class="viz-title">${title}</span>
                <div class="viz-controls">
                    <button class="viz-btn" id="${container.id}-reset">New Array</button>
                    <button class="viz-btn primary" id="${container.id}-run">▶ Run</button>
                    <button class="viz-btn" id="${container.id}-step">Step</button>
                    <label class="viz-speed-label">Speed
                        <input type="range" class="viz-speed" id="${container.id}-speed" min="50" max="1000" value="400" step="50"/>
                    </label>
                    ${extraControls}
                </div>
            </div>
            <div class="viz-bars-wrap">
                <div class="viz-bars" id="${container.id}-bars"></div>
            </div>
            <div class="viz-legend">
                <span class="legend-item"><span class="legend-dot default"></span>Unsorted</span>
                <span class="legend-item"><span class="legend-dot comparing"></span>Comparing</span>
                <span class="legend-item"><span class="legend-dot swapping"></span>Swapping</span>
                <span class="legend-item"><span class="legend-dot sorted"></span>Sorted</span>
            </div>
            <div class="viz-status" id="${container.id}-status">Press Run or Step to start</div>
            <div class="viz-stats">
                <span>Comparisons: <strong id="${container.id}-comps">0</strong></span>
                <span>Swaps: <strong id="${container.id}-swaps">0</strong></span>
                <span>Steps: <strong id="${container.id}-steps">0</strong></span>
            </div>
        </div>
    `;
}

function renderBars(barsEl, arr, states = {}) {
    // states: { [index]: 'comparing' | 'swapping' | 'sorted' | 'pivot' | 'found' }
    const max = Math.max(...arr);
    barsEl.innerHTML = arr.map((val, i) => {
        const state = states[i] || 'default';
        const pct = Math.round((val / max) * 100);
        return `<div class="viz-bar ${state}" style="height:${pct}%" title="${val}">
                    <span class="bar-label">${val}</span>
                </div>`;
    }).join('');
}

/* ═══════════════════════════════════════
   VISUALIZERS MAP
═══════════════════════════════════════ */
const VISUALIZERS = {};

/* ─── BUBBLE SORT ─── */
VISUALIZERS['bubble-sort'] = function(container) {
    buildVizShell(container, 'Bubble Sort Visualizer');
    const cid = container.id;
    const barsEl    = document.getElementById(`${cid}-bars`);
    const statusEl  = document.getElementById(`${cid}-status`);
    const compsEl   = document.getElementById(`${cid}-comps`);
    const swapsEl   = document.getElementById(`${cid}-swaps`);
    const stepsEl   = document.getElementById(`${cid}-steps`);
    const speedEl   = document.getElementById(`${cid}-speed`);
    const runBtn    = document.getElementById(`${cid}-run`);
    const stepBtn   = document.getElementById(`${cid}-step`);
    const resetBtn  = document.getElementById(`${cid}-reset`);

    let arr = makeArray(12);
    let steps = [];
    let stepIndex = 0;
    let running = false;
    let stats = { comps: 0, swaps: 0, steps: 0 };

    function reset() {
        arr = makeArray(12);
        steps = generateSteps([...arr]);
        stepIndex = 0;
        running = false;
        stats = { comps: 0, swaps: 0, steps: 0 };
        compsEl.textContent = swapsEl.textContent = stepsEl.textContent = '0';
        statusEl.textContent = 'Press Run or Step to start';
        runBtn.textContent = '▶ Run';
        renderBars(barsEl, arr, {});
    }

    function generateSteps(a) {
        const s = [];
        const n = a.length;
        for (let i = 0; i < n; i++) {
            let swapped = false;
            for (let j = 0; j < n - i - 1; j++) {
                s.push({ type: 'compare', i: j, j: j+1, arr: [...a], sortedFrom: n-i });
                if (a[j] > a[j+1]) {
                    [a[j], a[j+1]] = [a[j+1], a[j]];
                    s.push({ type: 'swap', i: j, j: j+1, arr: [...a], sortedFrom: n-i });
                    swapped = true;
                }
            }
            s.push({ type: 'sorted', sortedFrom: n-i-1, arr: [...a] });
            if (!swapped) { s.push({ type: 'done', arr: [...a] }); break; }
        }
        s.push({ type: 'done', arr: [...a] });
        return s;
    }

    function applyStep(step) {
        const states = {};
        if (step.type === 'compare') {
            states[step.i] = 'comparing'; states[step.j] = 'comparing';
            statusEl.textContent = `Comparing ${step.arr[step.i]} and ${step.arr[step.j]}`;
            stats.comps++;
        } else if (step.type === 'swap') {
            states[step.i] = 'swapping'; states[step.j] = 'swapping';
            statusEl.textContent = `Swapping ${step.arr[step.j]} and ${step.arr[step.i]}`;
            stats.swaps++;
        } else if (step.type === 'sorted') {
            for (let k = step.sortedFrom; k < step.arr.length; k++) states[k] = 'sorted';
            statusEl.textContent = `Pass complete — rightmost elements are sorted`;
        } else if (step.type === 'done') {
            step.arr.forEach((_, k) => states[k] = 'sorted');
            statusEl.textContent = '✅ Array fully sorted!';
            running = false; runBtn.textContent = '▶ Run';
        }
        // Carry sorted state forward
        if (step.sortedFrom !== undefined) {
            for (let k = step.sortedFrom; k < step.arr.length; k++) states[k] = 'sorted';
        }
        stats.steps++;
        compsEl.textContent = stats.comps;
        swapsEl.textContent = stats.swaps;
        stepsEl.textContent = stats.steps;
        renderBars(barsEl, step.arr, states);
        arr = step.arr;
    }

    async function runAll() {
        if (running) { running = false; runBtn.textContent = '▶ Run'; return; }
        if (stepIndex >= steps.length) reset();
        running = true;
        runBtn.textContent = '⏸ Pause';
        while (stepIndex < steps.length && running) {
            applyStep(steps[stepIndex++]);
            const delay = 1050 - parseInt(speedEl.value);
            await sleep(delay);
        }
        if (stepIndex >= steps.length) { running = false; runBtn.textContent = '▶ Run'; }
    }

    stepBtn.addEventListener('click', () => {
        if (stepIndex < steps.length) applyStep(steps[stepIndex++]);
    });
    runBtn.addEventListener('click', runAll);
    resetBtn.addEventListener('click', reset);
    reset();
};

/* ─── BINARY SEARCH ─── */
VISUALIZERS['binary-search'] = function(container) {
    container.innerHTML = `
        <div class="viz-shell">
            <div class="viz-header">
                <span class="viz-title">Binary Search Visualizer</span>
                <div class="viz-controls">
                    <button class="viz-btn" id="${container.id}-reset">New Array</button>
                    <input type="number" class="viz-target-input" id="${container.id}-target" placeholder="Target value"/>
                    <button class="viz-btn primary" id="${container.id}-run">▶ Search</button>
                    <button class="viz-btn" id="${container.id}-step">Step</button>
                    <label class="viz-speed-label">Speed
                        <input type="range" class="viz-speed" id="${container.id}-speed" min="200" max="1200" value="600" step="100"/>
                    </label>
                </div>
            </div>
            <div class="viz-bars-wrap">
                <div class="viz-bars" id="${container.id}-bars"></div>
            </div>
            <div class="viz-legend">
                <span class="legend-item"><span class="legend-dot default"></span>Search Space</span>
                <span class="legend-item"><span class="legend-dot comparing"></span>Mid (checking)</span>
                <span class="legend-item"><span class="legend-dot" style="background:#9f7aea"></span>Eliminated</span>
                <span class="legend-item"><span class="legend-dot sorted"></span>Found!</span>
            </div>
            <div class="viz-status" id="${container.id}-status">Enter a target and press Search</div>
            <div class="viz-stats">
                <span>Comparisons: <strong id="${container.id}-comps">0</strong></span>
                <span>Steps: <strong id="${container.id}-steps">0</strong></span>
            </div>
        </div>
    `;

    const cid = container.id;
    const barsEl   = document.getElementById(`${cid}-bars`);
    const statusEl = document.getElementById(`${cid}-status`);
    const compsEl  = document.getElementById(`${cid}-comps`);
    const stepsEl  = document.getElementById(`${cid}-steps`);
    const speedEl  = document.getElementById(`${cid}-speed`);
    const runBtn   = document.getElementById(`${cid}-run`);
    const stepBtn  = document.getElementById(`${cid}-step`);
    const resetBtn = document.getElementById(`${cid}-reset`);
    const targetEl = document.getElementById(`${cid}-target`);

    let arr = [], steps = [], stepIndex = 0, running = false;
    let stats = { comps: 0, steps: 0 };

    function reset() {
        arr = makeArray(14, 5, 95).sort((a, b) => a - b);
        // Remove duplicates
        arr = [...new Set(arr)].slice(0, 14);
        steps = []; stepIndex = 0; running = false;
        stats = { comps: 0, steps: 0 };
        compsEl.textContent = stepsEl.textContent = '0';
        statusEl.textContent = 'Enter a target value and press Search';
        renderBars(barsEl, arr, {});
    }

    function generateSteps(a, target) {
        const s = [];
        let lo = 0, hi = a.length - 1;
        const eliminated = {};
        while (lo <= hi) {
            const mid = Math.floor((lo + hi) / 2);
            s.push({ lo, hi, mid, target, arr: [...a], eliminated: {...eliminated}, type: 'compare' });
            if (a[mid] === target) {
                s.push({ lo, hi, mid, target, arr: [...a], eliminated: {...eliminated}, type: 'found' }); return s;
            } else if (a[mid] < target) {
                for (let k = lo; k <= mid; k++) eliminated[k] = true;
                lo = mid + 1;
                s.push({ lo, hi, mid, target, arr: [...a], eliminated: {...eliminated}, type: 'shift', dir: 'right' });
            } else {
                for (let k = mid; k <= hi; k++) eliminated[k] = true;
                hi = mid - 1;
                s.push({ lo, hi, mid, target, arr: [...a], eliminated: {...eliminated}, type: 'shift', dir: 'left' });
            }
        }
        s.push({ type: 'notfound', arr: [...a], eliminated: {...eliminated} });
        return s;
    }

    function applyStep(step) {
        const states = {};
        for (const k in step.eliminated) states[k] = 'pivot'; // purple = eliminated
        if (step.type === 'compare') {
            states[step.mid] = 'comparing';
            statusEl.textContent = `Checking mid=${step.mid}: arr[${step.mid}]=${step.arr[step.mid]} vs target=${step.target}`;
            stats.comps++;
        } else if (step.type === 'shift') {
            states[step.mid] = 'pivot';
            statusEl.textContent = step.dir === 'right'
                ? `${step.arr[step.mid]} < ${step.target} → search RIGHT half`
                : `${step.arr[step.mid]} > ${step.target} → search LEFT half`;
        } else if (step.type === 'found') {
            step.arr.forEach((_, k) => states[k] = k === step.mid ? 'sorted' : 'pivot');
            statusEl.textContent = `✅ Found ${step.target} at index ${step.mid}!`;
            running = false; runBtn.textContent = '▶ Search';
        } else if (step.type === 'notfound') {
            step.arr.forEach((_, k) => states[k] = 'pivot');
            statusEl.textContent = `❌ ${step.target} not found in the array`;
            running = false; runBtn.textContent = '▶ Search';
        }
        stats.steps++;
        compsEl.textContent = stats.comps;
        stepsEl.textContent = stats.steps;
        renderBars(barsEl, step.arr, states);
    }

    async function runAll() {
        const target = parseInt(targetEl.value);
        if (isNaN(target)) { statusEl.textContent = 'Please enter a valid target number'; return; }
        if (steps.length === 0 || stepIndex === 0) {
            steps = generateSteps([...arr], target);
            stepIndex = 0; stats = { comps: 0, steps: 0 };
        }
        if (running) { running = false; runBtn.textContent = '▶ Search'; return; }
        running = true; runBtn.textContent = '⏸ Pause';
        while (stepIndex < steps.length && running) {
            applyStep(steps[stepIndex++]);
            await sleep(1250 - parseInt(speedEl.value));
        }
        running = false; runBtn.textContent = '▶ Search';
    }

    stepBtn.addEventListener('click', () => {
        const target = parseInt(targetEl.value);
        if (isNaN(target)) { statusEl.textContent = 'Enter a target number first'; return; }
        if (steps.length === 0) { steps = generateSteps([...arr], target); stepIndex = 0; }
        if (stepIndex < steps.length) applyStep(steps[stepIndex++]);
    });
    runBtn.addEventListener('click', runAll);
    resetBtn.addEventListener('click', () => { steps = []; reset(); });
    reset();
};

/* ─── LINEAR SEARCH ─── */
VISUALIZERS['linear-search'] = function(container) {
    container.innerHTML = `
        <div class="viz-shell">
            <div class="viz-header">
                <span class="viz-title">Linear Search Visualizer</span>
                <div class="viz-controls">
                    <button class="viz-btn" id="${container.id}-reset">New Array</button>
                    <input type="number" class="viz-target-input" id="${container.id}-target" placeholder="Target value"/>
                    <button class="viz-btn primary" id="${container.id}-run">▶ Search</button>
                    <button class="viz-btn" id="${container.id}-step">Step</button>
                    <label class="viz-speed-label">Speed
                        <input type="range" class="viz-speed" id="${container.id}-speed" min="100" max="800" value="400" step="50"/>
                    </label>
                </div>
            </div>
            <div class="viz-bars-wrap">
                <div class="viz-bars" id="${container.id}-bars"></div>
            </div>
            <div class="viz-legend">
                <span class="legend-item"><span class="legend-dot default"></span>Unvisited</span>
                <span class="legend-item"><span class="legend-dot comparing"></span>Checking</span>
                <span class="legend-item"><span class="legend-dot" style="background:#9f7aea"></span>Checked</span>
                <span class="legend-item"><span class="legend-dot sorted"></span>Found!</span>
            </div>
            <div class="viz-status" id="${container.id}-status">Enter a target and press Search</div>
            <div class="viz-stats">
                <span>Checked: <strong id="${container.id}-comps">0</strong></span>
                <span>Steps: <strong id="${container.id}-steps">0</strong></span>
            </div>
        </div>
    `;

    const cid = container.id;
    const barsEl   = document.getElementById(`${cid}-bars`);
    const statusEl = document.getElementById(`${cid}-status`);
    const compsEl  = document.getElementById(`${cid}-comps`);
    const stepsEl  = document.getElementById(`${cid}-steps`);
    const speedEl  = document.getElementById(`${cid}-speed`);
    const runBtn   = document.getElementById(`${cid}-run`);
    const stepBtn  = document.getElementById(`${cid}-step`);
    const resetBtn = document.getElementById(`${cid}-reset`);
    const targetEl = document.getElementById(`${cid}-target`);

    let arr = [], steps = [], stepIndex = 0, running = false;
    let stats = { comps: 0, steps: 0 };

    function reset() {
        arr = makeArray(14, 5, 99);
        steps = []; stepIndex = 0; running = false;
        stats = { comps: 0, steps: 0 };
        compsEl.textContent = stepsEl.textContent = '0';
        statusEl.textContent = 'Enter a target value and press Search';
        renderBars(barsEl, arr, {});
    }

    function generateSteps(a, target) {
        const s = [];
        const checked = {};
        for (let i = 0; i < a.length; i++) {
            s.push({ idx: i, arr: [...a], checked: {...checked}, type: 'compare', target });
            if (a[i] === target) { s.push({ idx: i, arr: [...a], checked, type: 'found', target }); return s; }
            checked[i] = true;
        }
        s.push({ arr: [...a], checked, type: 'notfound', target });
        return s;
    }

    function applyStep(step) {
        const states = {};
        for (const k in step.checked) states[k] = 'pivot';
        if (step.type === 'compare') {
            states[step.idx] = 'comparing';
            statusEl.textContent = `Checking index ${step.idx}: arr[${step.idx}] = ${step.arr[step.idx]} — is it ${step.target}?`;
            stats.comps++;
        } else if (step.type === 'found') {
            step.arr.forEach((_, k) => states[k] = k === step.idx ? 'sorted' : (step.checked[k] ? 'pivot' : 'default'));
            statusEl.textContent = `✅ Found ${step.target} at index ${step.idx}!`;
            running = false; runBtn.textContent = '▶ Search';
        } else if (step.type === 'notfound') {
            step.arr.forEach((_, k) => states[k] = 'pivot');
            statusEl.textContent = `❌ ${step.target} not found after checking all ${step.arr.length} elements`;
            running = false; runBtn.textContent = '▶ Search';
        }
        stats.steps++;
        compsEl.textContent = stats.comps;
        stepsEl.textContent = stats.steps;
        renderBars(barsEl, step.arr, states);
    }

    async function runAll() {
        const target = parseInt(targetEl.value);
        if (isNaN(target)) { statusEl.textContent = 'Please enter a valid target number'; return; }
        if (!steps.length) { steps = generateSteps([...arr], target); stepIndex = 0; stats = { comps: 0, steps: 0 }; }
        if (running) { running = false; runBtn.textContent = '▶ Search'; return; }
        running = true; runBtn.textContent = '⏸ Pause';
        while (stepIndex < steps.length && running) {
            applyStep(steps[stepIndex++]);
            await sleep(850 - parseInt(speedEl.value));
        }
        running = false; runBtn.textContent = '▶ Search';
    }

    stepBtn.addEventListener('click', () => {
        const target = parseInt(targetEl.value);
        if (isNaN(target)) { statusEl.textContent = 'Enter a target number first'; return; }
        if (!steps.length) { steps = generateSteps([...arr], target); stepIndex = 0; }
        if (stepIndex < steps.length) applyStep(steps[stepIndex++]);
    });
    runBtn.addEventListener('click', runAll);
    resetBtn.addEventListener('click', () => { steps = []; reset(); });
    reset();
};

/* ─── INSERTION SORT ─── */
VISUALIZERS['insertion-sort'] = function(container) {
    buildVizShell(container, 'Insertion Sort Visualizer');
    const cid = container.id;
    const barsEl   = document.getElementById(`${cid}-bars`);
    const statusEl = document.getElementById(`${cid}-status`);
    const compsEl  = document.getElementById(`${cid}-comps`);
    const swapsEl  = document.getElementById(`${cid}-swaps`);
    const stepsEl  = document.getElementById(`${cid}-steps`);
    const speedEl  = document.getElementById(`${cid}-speed`);
    const runBtn   = document.getElementById(`${cid}-run`);
    const stepBtn  = document.getElementById(`${cid}-step`);
    const resetBtn = document.getElementById(`${cid}-reset`);

    let arr = [], steps = [], stepIndex = 0, running = false;
    let stats = { comps: 0, swaps: 0, steps: 0 };

    function reset() {
        arr = makeArray(12);
        steps = generateSteps([...arr]);
        stepIndex = 0; running = false;
        stats = { comps: 0, swaps: 0, steps: 0 };
        compsEl.textContent = swapsEl.textContent = stepsEl.textContent = '0';
        statusEl.textContent = 'Press Run or Step to start';
        runBtn.textContent = '▶ Run';
        renderBars(barsEl, arr, { 0: 'sorted' });
    }

    function generateSteps(a) {
        const s = [];
        const n = a.length;
        s.push({ arr: [...a], sortedUpTo: 0, type: 'init' });
        for (let i = 1; i < n; i++) {
            let j = i;
            s.push({ arr: [...a], i, j, type: 'pick', sortedUpTo: i-1 });
            while (j > 0 && a[j-1] > a[j]) {
                s.push({ arr: [...a], i, j, type: 'compare', sortedUpTo: i-1 });
                [a[j-1], a[j]] = [a[j], a[j-1]];
                s.push({ arr: [...a], i, j, type: 'shift', sortedUpTo: i-1 });
                j--;
            }
            s.push({ arr: [...a], i, sortedUpTo: i, type: 'inserted' });
        }
        s.push({ arr: [...a], type: 'done' });
        return s;
    }

    function applyStep(step) {
        const states = {};
        if (step.sortedUpTo !== undefined) {
            for (let k = 0; k <= step.sortedUpTo; k++) states[k] = 'sorted';
        }
        if (step.type === 'pick') {
            states[step.j] = 'swapping';
            statusEl.textContent = `Picking element ${step.arr[step.j]} at index ${step.j} to insert`;
        } else if (step.type === 'compare') {
            states[step.j-1] = 'comparing'; states[step.j] = 'swapping';
            statusEl.textContent = `${step.arr[step.j-1]} > ${step.arr[step.j]} — shifting left`;
            stats.comps++;
        } else if (step.type === 'shift') {
            states[step.j-1] = 'swapping'; states[step.j] = 'comparing';
            statusEl.textContent = `Shifted ${step.arr[step.j-1]} one position right`;
            stats.swaps++;
        } else if (step.type === 'inserted') {
            statusEl.textContent = `Inserted — sorted region now covers 0..${step.sortedUpTo}`;
        } else if (step.type === 'done') {
            step.arr.forEach((_, k) => states[k] = 'sorted');
            statusEl.textContent = '✅ Array fully sorted!';
            running = false; runBtn.textContent = '▶ Run';
        }
        stats.steps++;
        compsEl.textContent = stats.comps;
        swapsEl.textContent = stats.swaps;
        stepsEl.textContent = stats.steps;
        renderBars(barsEl, step.arr, states);
    }

    async function runAll() {
        if (running) { running = false; runBtn.textContent = '▶ Run'; return; }
        if (stepIndex >= steps.length) reset();
        running = true; runBtn.textContent = '⏸ Pause';
        while (stepIndex < steps.length && running) {
            applyStep(steps[stepIndex++]);
            await sleep(1050 - parseInt(speedEl.value));
        }
        running = false; runBtn.textContent = '▶ Run';
    }

    stepBtn.addEventListener('click', () => { if (stepIndex < steps.length) applyStep(steps[stepIndex++]); });
    runBtn.addEventListener('click', runAll);
    resetBtn.addEventListener('click', reset);
    reset();
};

/* ─── SELECTION SORT ─── */
VISUALIZERS['selection-sort'] = function(container) {
    buildVizShell(container, 'Selection Sort Visualizer');
    const cid = container.id;
    const barsEl   = document.getElementById(`${cid}-bars`);
    const statusEl = document.getElementById(`${cid}-status`);
    const compsEl  = document.getElementById(`${cid}-comps`);
    const swapsEl  = document.getElementById(`${cid}-swaps`);
    const stepsEl  = document.getElementById(`${cid}-steps`);
    const speedEl  = document.getElementById(`${cid}-speed`);
    const runBtn   = document.getElementById(`${cid}-run`);
    const stepBtn  = document.getElementById(`${cid}-step`);
    const resetBtn = document.getElementById(`${cid}-reset`);

    let arr = [], steps = [], stepIndex = 0, running = false;
    let stats = { comps: 0, swaps: 0, steps: 0 };

    function reset() {
        arr = makeArray(12);
        steps = generateSteps([...arr]);
        stepIndex = 0; running = false;
        stats = { comps: 0, swaps: 0, steps: 0 };
        compsEl.textContent = swapsEl.textContent = stepsEl.textContent = '0';
        statusEl.textContent = 'Press Run or Step to start';
        runBtn.textContent = '▶ Run';
        renderBars(barsEl, arr, {});
    }

    function generateSteps(a) {
        const s = [];
        const n = a.length;
        for (let i = 0; i < n - 1; i++) {
            let minIdx = i;
            s.push({ arr: [...a], i, minIdx, j: i, type: 'start-pass', sortedUpTo: i-1 });
            for (let j = i + 1; j < n; j++) {
                s.push({ arr: [...a], i, minIdx, j, type: 'compare', sortedUpTo: i-1 });
                if (a[j] < a[minIdx]) { minIdx = j; }
                s.push({ arr: [...a], i, minIdx, j, type: 'check-min', sortedUpTo: i-1 });
            }
            if (minIdx !== i) {
                [a[i], a[minIdx]] = [a[minIdx], a[i]];
                s.push({ arr: [...a], i, minIdx, type: 'swap', sortedUpTo: i });
            } else {
                s.push({ arr: [...a], i, type: 'no-swap', sortedUpTo: i });
            }
        }
        s.push({ arr: [...a], type: 'done' });
        return s;
    }

    function applyStep(step) {
        const states = {};
        if (step.sortedUpTo !== undefined) for (let k = 0; k <= step.sortedUpTo; k++) states[k] = 'sorted';
        if (step.type === 'compare') {
            states[step.i] = 'swapping'; states[step.j] = 'comparing'; states[step.minIdx] = 'pivot';
            statusEl.textContent = `Comparing arr[${step.j}]=${step.arr[step.j]} with current min arr[${step.minIdx}]=${step.arr[step.minIdx]}`;
            stats.comps++;
        } else if (step.type === 'check-min') {
            states[step.minIdx] = 'pivot';
            statusEl.textContent = `Current minimum: arr[${step.minIdx}] = ${step.arr[step.minIdx]}`;
        } else if (step.type === 'swap') {
            states[step.i] = 'swapping'; states[step.minIdx] = 'swapping';
            statusEl.textContent = `Swapping minimum ${step.arr[step.i]} into position ${step.i}`;
            stats.swaps++;
        } else if (step.type === 'no-swap') {
            statusEl.textContent = `Minimum already at position ${step.i} — no swap needed`;
        } else if (step.type === 'done') {
            step.arr.forEach((_, k) => states[k] = 'sorted');
            statusEl.textContent = '✅ Array fully sorted!';
            running = false; runBtn.textContent = '▶ Run';
        }
        stats.steps++;
        compsEl.textContent = stats.comps;
        swapsEl.textContent = stats.swaps;
        stepsEl.textContent = stats.steps;
        renderBars(barsEl, step.arr, states);
    }

    async function runAll() {
        if (running) { running = false; runBtn.textContent = '▶ Run'; return; }
        if (stepIndex >= steps.length) reset();
        running = true; runBtn.textContent = '⏸ Pause';
        while (stepIndex < steps.length && running) {
            applyStep(steps[stepIndex++]);
            await sleep(1050 - parseInt(speedEl.value));
        }
        running = false; runBtn.textContent = '▶ Run';
    }

    stepBtn.addEventListener('click', () => { if (stepIndex < steps.length) applyStep(steps[stepIndex++]); });
    runBtn.addEventListener('click', runAll);
    resetBtn.addEventListener('click', reset);
    reset();
};