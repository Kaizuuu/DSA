package com.example.website.controller;

import com.example.website.model.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final List<Topic> topics = buildTopics();

    private static List<Topic> buildTopics() {
        List<Topic> list = new ArrayList<>();

        list.add(new Topic("list", "List", "Data Structures",
                "Learn about Python lists and their operations", "_List.mp4", "#1a1a2e", "📋",
                new String[]{"list", "python", "basics"})
                .withComplexity("O(1)", "O(n)", "O(n)",
                        "Access by index", "Search/insert", "Insert at beginning",
                        "O(n)", "Stores n elements")
                .withCode(
                        "LIST OPERATIONS:\n  append(x)   → add x to end       O(1)\n  insert(i,x) → add x at index i   O(n)\n  remove(x)   → remove first x     O(n)\n  pop(i)      → remove at index i  O(n)\n  index(x)    → find first x       O(n)\n  sort()      → sort in place      O(n log n)",
                        "# Python List Operations\nmy_list = []\n\n# Append - O(1)\nmy_list.append(42)\n\n# Insert at index - O(n)\nmy_list.insert(0, 10)\n\n# Access by index - O(1)\nval = my_list[0]\n\n# Remove element - O(n)\nmy_list.remove(42)\n\n# Slice - O(k)\nsublist = my_list[1:3]\n\n# List comprehension\nsquares = [x**2 for x in range(10)]\nprint(squares)"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🛒", "Shopping Cart", "Dynamic list of items added/removed by a user in e-commerce apps"),
                        new Topic.UseCase("📜", "History Log", "Storing ordered sequences of actions or events"),
                        new Topic.UseCase("🎮", "Game Inventory", "Managing player items with dynamic add/remove")
                ))
                .withInterviewQuestions(
                        List.of("How do Python lists differ from arrays?", "What is the time complexity of list.append()?", "How would you remove duplicates from a list?"),
                        List.of("Easy", "Easy", "Medium")
                )
                .withVisualizer(false));

        list.add(new Topic("array", "Array", "Data Structures",
                "Understanding arrays and indexing", "ARRAY.mp4", "#16213e", "🔢",
                new String[]{"array", "index", "basics"})
                .withComplexity("O(1)", "O(n)", "O(n)",
                        "Random access by index", "Search unsorted", "Insert/delete at start",
                        "O(n)", "Fixed n elements")
                .withCode(
                        "ARRAY ACCESS:\n  Read  arr[i]     → O(1)\n  Write arr[i] = x → O(1)\n  Search           → O(n)\n  Insert (end)     → O(1) amortized\n  Insert (middle)  → O(n)",
                        "# Python array using list\nimport array\n\n# Create typed array (int)\narr = array.array('i', [1, 2, 3, 4, 5])\n\n# Access - O(1)\nprint(arr[2])  # 3\n\n# Update - O(1)\narr[2] = 99\n\n# Iterate\nfor val in arr:\n    print(val)\n\n# 2D array with list of lists\nmatrix = [[0] * 3 for _ in range(3)]"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🖼️", "Image Pixels", "2D pixel arrays power every image editor and graphics engine"),
                        new Topic.UseCase("📊", "Statistics", "Numerical computing libraries use contiguous arrays for fast math"),
                        new Topic.UseCase("🎵", "Audio Buffers", "Sound waveforms stored as arrays of amplitude values")
                ))
                .withInterviewQuestions(
                        List.of("What is the difference between an array and a linked list?", "How do you find the maximum subarray sum?", "Explain two-pointer technique on arrays."),
                        List.of("Easy", "Hard", "Medium")
                ));

        list.add(new Topic("bubble-sort", "Bubble Sort", "Sorting",
                "Classic comparison-based sorting algorithm", "BUBBLE SORT.mp4", "#533483", "🫧",
                new String[]{"sort", "comparison", "O(n²)"})
                .withComplexity("O(n)", "O(n²)", "O(n²)",
                        "Already sorted (with early exit)", "Random data", "Reverse sorted",
                        "O(1)", "In-place, no extra memory")
                .withCode(
                        "BUBBLE SORT:\n  for i from 0 to n-1:\n    swapped = false\n    for j from 0 to n-i-1:\n      if arr[j] > arr[j+1]:\n        swap arr[j] and arr[j+1]\n        swapped = true\n    if not swapped:\n      break  ← early exit optimization",
                        "def bubble_sort(arr):\n    n = len(arr)\n    for i in range(n):\n        swapped = False\n        for j in range(0, n - i - 1):\n            if arr[j] > arr[j + 1]:\n                arr[j], arr[j + 1] = arr[j + 1], arr[j]\n                swapped = True\n        # Early exit if already sorted\n        if not swapped:\n            break\n    return arr\n\n# Example\nnums = [64, 34, 25, 12, 22, 11, 90]\nprint(bubble_sort(nums))  # [11, 12, 22, 25, 34, 64, 90]"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("📚", "Education", "Ideal for teaching sorting concepts due to its visual simplicity"),
                        new Topic.UseCase("🔍", "Nearly Sorted Data", "Efficient when input is almost sorted due to early exit"),
                        new Topic.UseCase("⚙️", "Small Datasets", "Acceptable performance for lists with fewer than ~20 elements")
                ))
                .withInterviewQuestions(
                        List.of("Why is bubble sort rarely used in production?", "What optimization makes bubble sort O(n) best case?", "Compare bubble sort vs insertion sort."),
                        List.of("Easy", "Easy", "Medium")
                )
                .withVisualizer(true));

        list.add(new Topic("binary-search", "Binary Search", "Searching",
                "Efficient searching in sorted arrays", "binary Search.mp4", "#0f3460", "🔍",
                new String[]{"search", "binary", "O(log n)"})
                .withComplexity("O(1)", "O(log n)", "O(log n)",
                        "Target is the middle element", "Target anywhere in array", "Target not found",
                        "O(1)", "Iterative uses no extra space")
                .withCode(
                        "BINARY SEARCH:\n  low = 0, high = n-1\n  while low <= high:\n    mid = (low + high) / 2\n    if arr[mid] == target:\n      return mid\n    elif arr[mid] < target:\n      low = mid + 1\n    else:\n      high = mid - 1\n  return -1  ← not found",
                        "def binary_search(arr, target):\n    low, high = 0, len(arr) - 1\n    while low <= high:\n        mid = (low + high) // 2\n        if arr[mid] == target:\n            return mid\n        elif arr[mid] < target:\n            low = mid + 1\n        else:\n            high = mid - 1\n    return -1  # not found\n\n# Example\nnums = [1, 3, 5, 7, 9, 11, 13]\nprint(binary_search(nums, 7))   # 3\nprint(binary_search(nums, 6))  # -1"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("📖", "Dictionary Lookup", "Finding words in sorted dictionaries or database indexes"),
                        new Topic.UseCase("🎮", "Game Leaderboards", "Quickly finding a player's rank in a sorted leaderboard"),
                        new Topic.UseCase("🔬", "Scientific Computing", "Root-finding algorithms use binary search on continuous functions")
                ))
                .withInterviewQuestions(
                        List.of("What is the prerequisite for binary search?", "How do you find the first/last occurrence of a duplicate?", "Apply binary search to find a peak element."),
                        List.of("Easy", "Medium", "Hard")
                )
                .withVisualizer(true));

        list.add(new Topic("linear-search", "Linear Search", "Searching",
                "Simple sequential search algorithm", "Copy of Linear Search.mp4", "#2c003e", "📡",
                new String[]{"search", "linear", "O(n)"})
                .withComplexity("O(1)", "O(n)", "O(n)",
                        "Target is the first element", "Target anywhere", "Target not in list",
                        "O(1)", "No extra space needed")
                .withCode(
                        "LINEAR SEARCH:\n  for i from 0 to n-1:\n    if arr[i] == target:\n      return i\n  return -1",
                        "def linear_search(arr, target):\n    for i, val in enumerate(arr):\n        if val == target:\n            return i\n    return -1\n\n# Works on unsorted data\ndata = [\"apple\", \"banana\", \"cherry\"]\nprint(linear_search(data, \"banana\"))  # 1"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("📋", "Unsorted Lists", "Only option when data cannot be sorted first"),
                        new Topic.UseCase("🔎", "Small Collections", "Simpler than binary search for tiny datasets"),
                        new Topic.UseCase("🗃️", "Linked Lists", "Only way to search a linked list without random access")
                ))
                .withInterviewQuestions(
                        List.of("When would you choose linear over binary search?", "How do you search for an element in an unsorted linked list?", "What is sentinel linear search?"),
                        List.of("Easy", "Easy", "Medium")
                )
                .withVisualizer(true));

        list.add(new Topic("insertion-sort", "Insertion Sort", "Sorting",
                "Building sorted array one element at a time", "Insertion sort.mp4", "#2d132c", "📥",
                new String[]{"sort", "insertion", "O(n²)"})
                .withComplexity("O(n)", "O(n²)", "O(n²)",
                        "Already sorted", "Random data", "Reverse sorted",
                        "O(1)", "Sorts in place")
                .withCode(
                        "INSERTION SORT:\n  for i from 1 to n-1:\n    key = arr[i]\n    j = i - 1\n    while j >= 0 and arr[j] > key:\n      arr[j+1] = arr[j]\n      j = j - 1\n    arr[j+1] = key",
                        "def insertion_sort(arr):\n    for i in range(1, len(arr)):\n        key = arr[i]\n        j = i - 1\n        while j >= 0 and arr[j] > key:\n            arr[j + 1] = arr[j]\n            j -= 1\n        arr[j + 1] = key\n    return arr\n\nnums = [12, 11, 13, 5, 6]\nprint(insertion_sort(nums))  # [5, 6, 11, 12, 13]"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🃏", "Card Sorting", "How humans naturally sort a hand of playing cards"),
                        new Topic.UseCase("📊", "Online Sorting", "Efficiently sorts data as it arrives in a stream"),
                        new Topic.UseCase("⚡", "Nearly Sorted", "Very fast on nearly sorted arrays compared to other O(n²)")
                ))
                .withInterviewQuestions(
                        List.of("Why is insertion sort preferred for small arrays?", "Is insertion sort stable? Explain.", "How does insertion sort compare to merge sort?"),
                        List.of("Easy", "Medium", "Medium")
                )
                .withVisualizer(true));

        list.add(new Topic("selection-sort", "Selection Sort", "Sorting",
                "Repeatedly finds the minimum element", "Selection sort.mp4", "#533483", "✅",
                new String[]{"sort", "selection", "O(n²)"})
                .withComplexity("O(n²)", "O(n²)", "O(n²)",
                        "Always the same", "Always the same", "Always O(n²)",
                        "O(1)", "In-place sorting")
                .withCode(
                        "SELECTION SORT:\n  for i from 0 to n-1:\n    min_idx = i\n    for j from i+1 to n:\n      if arr[j] < arr[min_idx]:\n        min_idx = j\n    swap arr[i] and arr[min_idx]",
                        "def selection_sort(arr):\n    n = len(arr)\n    for i in range(n):\n        min_idx = i\n        for j in range(i + 1, n):\n            if arr[j] < arr[min_idx]:\n                min_idx = j\n        arr[i], arr[min_idx] = arr[min_idx], arr[i]\n    return arr\n\nnums = [64, 25, 12, 22, 11]\nprint(selection_sort(nums))  # [11, 12, 22, 25, 64]"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("💾", "Minimizing Writes", "Useful when memory write operations are costly"),
                        new Topic.UseCase("📚", "Teaching", "Simple to understand — good intro to sorting"),
                        new Topic.UseCase("🔢", "Small Fixed Sets", "Acceptable for very small, bounded collections")
                ))
                .withInterviewQuestions(
                        List.of("Is selection sort stable? Why?", "What is the minimum number of swaps to sort an array?", "Compare selection sort and bubble sort."),
                        List.of("Medium", "Hard", "Easy")
                )
                .withVisualizer(true));

        list.add(new Topic("linked-list", "Linked Lists", "Data Structures",
                "Dynamic linear data structure with nodes", "Linked lists in Python.mp4", "#1a1a2e", "🔗",
                new String[]{"linked list", "nodes", "pointer"})
                .withComplexity("O(1)", "O(n)", "O(n)",
                        "Insert/delete at head", "Search or access by index", "Search when not present",
                        "O(n)", "Each node stores data + pointer")
                .withCode(
                        "NODE:\n  data\n  next → pointer to next node\n\nINSERT AT HEAD:\n  new_node.next = head\n  head = new_node\n\nTRAVERSE:\n  current = head\n  while current != null:\n    visit current.data\n    current = current.next",
                        "class Node:\n    def __init__(self, data):\n        self.data = data\n        self.next = None\n\nclass LinkedList:\n    def __init__(self):\n        self.head = None\n\n    def append(self, data):\n        new_node = Node(data)\n        if not self.head:\n            self.head = new_node\n            return\n        curr = self.head\n        while curr.next:\n            curr = curr.next\n        curr.next = new_node\n\n    def display(self):\n        vals = []\n        curr = self.head\n        while curr:\n            vals.append(str(curr.data))\n            curr = curr.next\n        print(' → '.join(vals))\n\nll = LinkedList()\nll.append(1); ll.append(2); ll.append(3)\nll.display()  # 1 → 2 → 3"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("↩️", "Undo/Redo", "Text editors use doubly linked lists for undo history"),
                        new Topic.UseCase("🎵", "Music Playlist", "Each song points to the next, enabling easy insertion"),
                        new Topic.UseCase("🖥️", "OS Memory", "Operating systems use linked lists to manage free memory blocks")
                ))
                .withInterviewQuestions(
                        List.of("How do you detect a cycle in a linked list?", "Reverse a linked list in-place.", "Find the middle of a linked list in one pass."),
                        List.of("Medium", "Medium", "Easy")
                ));

        list.add(new Topic("stack", "Stacks", "Data Structures",
                "Last-In-First-Out data structure", "Stacks.mp4", "#1b1b2f", "📚",
                new String[]{"stack", "LIFO", "push/pop"})
                .withComplexity("O(1)", "O(1)", "O(n)",
                        "Push/pop at top", "Push/pop/peek", "Search for element",
                        "O(n)", "Stores n elements")
                .withCode(
                        "STACK OPERATIONS:\n  push(x)  → add x to top    O(1)\n  pop()    → remove top      O(1)\n  peek()   → view top        O(1)\n  isEmpty()→ check if empty  O(1)",
                        "class Stack:\n    def __init__(self):\n        self.items = []\n\n    def push(self, item):\n        self.items.append(item)\n\n    def pop(self):\n        if self.is_empty():\n            raise IndexError('Stack underflow')\n        return self.items.pop()\n\n    def peek(self):\n        return self.items[-1] if self.items else None\n\n    def is_empty(self):\n        return len(self.items) == 0\n\ns = Stack()\ns.push(1); s.push(2); s.push(3)\nprint(s.pop())   # 3\nprint(s.peek())  # 2"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("↩️", "Undo Operations", "Ctrl+Z in any editor uses a stack to reverse actions"),
                        new Topic.UseCase("🌐", "Browser History", "Back button pops the previous URL off a stack"),
                        new Topic.UseCase("📐", "Expression Parsing", "Balanced parentheses and math expression evaluation")
                ))
                .withInterviewQuestions(
                        List.of("Implement a stack using two queues.", "Design a min-stack with O(1) getMin().", "Check balanced parentheses using a stack."),
                        List.of("Medium", "Medium", "Easy")
                ));

        list.add(new Topic("queue", "Queue", "Data Structures",
                "First-In-First-Out data structure", "QUEUE.mp4", "#0f3460", "🚶",
                new String[]{"queue", "FIFO", "enqueue"})
                .withComplexity("O(1)", "O(1)", "O(n)",
                        "Enqueue/dequeue", "Enqueue/dequeue", "Search for element",
                        "O(n)", "Stores n elements")
                .withCode(
                        "QUEUE OPERATIONS:\n  enqueue(x) → add to rear   O(1)\n  dequeue()  → remove front  O(1)\n  peek()     → view front    O(1)\n  isEmpty()  → check empty   O(1)",
                        "from collections import deque\n\nclass Queue:\n    def __init__(self):\n        self.items = deque()\n\n    def enqueue(self, item):\n        self.items.append(item)\n\n    def dequeue(self):\n        if self.is_empty():\n            raise IndexError('Queue is empty')\n        return self.items.popleft()\n\n    def peek(self):\n        return self.items[0] if self.items else None\n\n    def is_empty(self):\n        return len(self.items) == 0\n\nq = Queue()\nq.enqueue('A'); q.enqueue('B'); q.enqueue('C')\nprint(q.dequeue())  # A\nprint(q.peek())     # B"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🖨️", "Print Queue", "Printer processes jobs in the order they are submitted"),
                        new Topic.UseCase("🌐", "BFS Traversal", "Breadth-first search uses a queue to explore level by level"),
                        new Topic.UseCase("🎫", "Task Scheduling", "OS scheduler manages CPU tasks as a ready queue")
                ))
                .withInterviewQuestions(
                        List.of("Implement a queue using two stacks.", "What is a circular queue and why use it?", "Explain priority queue vs regular queue."),
                        List.of("Medium", "Medium", "Easy")
                ));

        list.add(new Topic("hash-table", "Hash Table", "Data Structures",
                "Efficient data storage with hash functions", "Hash Table.mp4", "#1b262c", "🗂️",
                new String[]{"hash", "O(1)", "collision"})
                .withComplexity("O(1)", "O(1)", "O(n)",
                        "No collisions", "Average case with good hash", "All keys collide (worst hash)",
                        "O(n)", "Stores keys and values")
                .withCode(
                        "HASH TABLE:\n  hash(key) → index\n  insert(key, val): table[hash(key)] = val\n  get(key):         return table[hash(key)]\n  delete(key):      table[hash(key)] = null\n\nCOLLISION HANDLING:\n  Chaining   → linked list at each slot\n  Open Addr. → probe for next empty slot",
                        "# Python dict is a hash table\nphone_book = {}\n\n# Insert - O(1)\nphone_book['Alice'] = '555-1234'\nphone_book['Bob']   = '555-5678'\n\n# Lookup - O(1)\nprint(phone_book['Alice'])  # 555-1234\n\n# Delete - O(1)\ndel phone_book['Bob']\n\n# Check existence - O(1)\nif 'Alice' in phone_book:\n    print('Found!')\n\n# Count frequencies\nwords = ['a', 'b', 'a', 'c', 'b', 'a']\nfreq = {}\nfor w in words:\n    freq[w] = freq.get(w, 0) + 1"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🔑", "Password Storage", "Hashed passwords stored so originals are never saved"),
                        new Topic.UseCase("⚡", "Caching", "Memoization and LRU cache rely on O(1) hash lookups"),
                        new Topic.UseCase("📊", "Word Frequency", "Count occurrences of words in a document instantly")
                ))
                .withInterviewQuestions(
                        List.of("What is a hash collision and how is it resolved?", "Design a hash map from scratch.", "Find two numbers that sum to a target using a hash table."),
                        List.of("Easy", "Hard", "Easy")
                ));

        list.add(new Topic("tree", "Tree", "Data Structures",
                "Hierarchical non-linear data structure", "tree.mp4", "#162447", "🌳",
                new String[]{"tree", "binary tree", "traversal"})
                .withComplexity("O(log n)", "O(log n)", "O(n)",
                        "Balanced BST search", "Average BST operations", "Skewed / degenerate tree",
                        "O(n)", "One node per element + pointers")
                .withCode(
                        "BINARY TREE NODE:\n  data, left, right\n\nINORDER TRAVERSAL (Left→Root→Right):\n  inorder(node):\n    if node is null: return\n    inorder(node.left)\n    visit node\n    inorder(node.right)\n\nBST INSERT:\n  if val < node.val → go left\n  if val > node.val → go right",
                        "class TreeNode:\n    def __init__(self, val):\n        self.val = val\n        self.left = None\n        self.right = None\n\ndef insert_bst(root, val):\n    if not root:\n        return TreeNode(val)\n    if val < root.val:\n        root.left = insert_bst(root.left, val)\n    else:\n        root.right = insert_bst(root.right, val)\n    return root\n\ndef inorder(root):\n    if root:\n        inorder(root.left)\n        print(root.val, end=' ')\n        inorder(root.right)\n\nroot = None\nfor v in [5, 3, 7, 1, 4]:\n    root = insert_bst(root, v)\ninorder(root)  # 1 3 4 5 7"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🗂️", "File Systems", "Directories and files form a tree (root → folders → files)"),
                        new Topic.UseCase("🌐", "DOM Tree", "HTML pages are parsed into a tree for browsers to render"),
                        new Topic.UseCase("🔍", "Database Indexes", "B-trees power MySQL and PostgreSQL indexes for fast queries")
                ))
                .withInterviewQuestions(
                        List.of("What are the tree traversal methods?", "How do you check if a binary tree is balanced?", "Find the lowest common ancestor of two nodes."),
                        List.of("Easy", "Medium", "Hard")
                ));

        list.add(new Topic("graphs", "Graphs", "Graph Theory",
                "Graph data structures and traversal algorithms", "Graphs.mp4", "#1f4068", "🕸️",
                new String[]{"graph", "BFS", "DFS"})
                .withComplexity("O(V+E)", "O(V+E)", "O(V²)",
                        "BFS/DFS traversal", "Adjacency list traversal", "Adjacency matrix traversal",
                        "O(V+E)", "Adjacency list representation")
                .withCode(
                        "GRAPH TRAVERSAL:\n\nBFS (Queue-based):\n  enqueue start\n  while queue not empty:\n    node = dequeue\n    visit node\n    enqueue unvisited neighbors\n\nDFS (Stack/Recursive):\n  visit node\n  mark visited\n  for each neighbor:\n    if not visited: DFS(neighbor)",
                        "from collections import defaultdict, deque\n\nclass Graph:\n    def __init__(self):\n        self.graph = defaultdict(list)\n\n    def add_edge(self, u, v):\n        self.graph[u].append(v)\n\n    def bfs(self, start):\n        visited = set([start])\n        queue = deque([start])\n        while queue:\n            node = queue.popleft()\n            print(node, end=' ')\n            for nbr in self.graph[node]:\n                if nbr not in visited:\n                    visited.add(nbr)\n                    queue.append(nbr)\n\n    def dfs(self, node, visited=None):\n        if visited is None: visited = set()\n        visited.add(node)\n        print(node, end=' ')\n        for nbr in self.graph[node]:\n            if nbr not in visited:\n                self.dfs(nbr, visited)\n\ng = Graph()\nfor u, v in [(0,1),(0,2),(1,2),(2,3)]:\n    g.add_edge(u, v)\ng.bfs(0)  # 0 1 2 3"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🗺️", "Navigation", "GPS routing finds shortest paths using graph algorithms"),
                        new Topic.UseCase("👥", "Social Networks", "Friend recommendations use graph traversal and clustering"),
                        new Topic.UseCase("🌐", "Web Crawling", "Search engines crawl the web as a directed graph of pages")
                ))
                .withInterviewQuestions(
                        List.of("What is the difference between BFS and DFS?", "How do you detect a cycle in a directed graph?", "Explain Dijkstra's shortest path algorithm."),
                        List.of("Easy", "Medium", "Hard")
                ));

        // Add remaining topics with basic complexity data
        list.add(new Topic("heaps", "Heaps", "Data Structures",
                "Priority queues and heap data structure", "Heaps.mp4", "#0d0d0d", "🏔️",
                new String[]{"heap", "priority queue", "O(log n)"})
                .withComplexity("O(1)", "O(log n)", "O(log n)",
                        "Peek at min/max", "Insert or extract", "Heapify operation",
                        "O(n)", "Array representation")
                .withCode(
                        "HEAP OPERATIONS:\n  insert(x)      → O(log n)  sift up\n  extract_min()  → O(log n)  sift down\n  peek()         → O(1)      view root\n  heapify(arr)   → O(n)      build heap",
                        "import heapq\n\n# Min-heap (default)\nheap = []\nheapq.heappush(heap, 5)\nheapq.heappush(heap, 1)\nheapq.heappush(heap, 3)\n\nprint(heapq.heappop(heap))  # 1 (min)\n\n# Max-heap (negate values)\nmax_heap = []\nheapq.heappush(max_heap, -5)\nheapq.heappush(max_heap, -1)\nprint(-heapq.heappop(max_heap))  # 5 (max)\n\n# Heapify existing list\nnums = [3, 1, 4, 1, 5, 9]\nheapq.heapify(nums)\nprint(nums[0])  # 1 (min)"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🏥", "Hospital Triage", "Priority queue ensures most critical patients seen first"),
                        new Topic.UseCase("🔀", "Heap Sort", "Used internally for an O(n log n) comparison sort"),
                        new Topic.UseCase("📡", "Dijkstra's Algorithm", "Min-heap drives efficient shortest path computation")
                ))
                .withInterviewQuestions(
                        List.of("How does a heap differ from a BST?", "Find the kth largest element using a heap.", "Merge k sorted lists efficiently."),
                        List.of("Easy", "Medium", "Hard")
                ));

        list.add(new Topic("matrix-tuple", "Matrix", "Data Structures",
                "Working with matrices and tuples in Python", "Copy of MATRIX TUPLE.mp4", "#1b1b2f", "🔲",
                new String[]{"matrix", "tuple", "python"})
                .withComplexity("O(1)", "O(n*m)", "O(n*m)",
                        "Element access by index", "Full traversal or search", "Matrix multiplication",
                        "O(n*m)", "n rows × m columns")
                .withCode(
                        "MATRIX OPERATIONS:\n  Access:    mat[i][j]         O(1)\n  Traverse:  nested loops      O(n*m)\n  Transpose: swap rows/cols    O(n*m)\n  Multiply:  A[n×k] × B[k×m]  O(n*k*m)",
                        "# 2D Matrix\nmatrix = [\n    [1, 2, 3],\n    [4, 5, 6],\n    [7, 8, 9]\n]\n\n# Access element\nprint(matrix[1][2])  # 6\n\n# Transpose\ntranspose = [[matrix[j][i] for j in range(3)] for i in range(3)]\n\n# Spiral traversal, rotate, search - common patterns\n# Tuple (immutable)\npoint = (3, 4)\nx, y = point  # unpacking\nprint(x, y)   # 3 4"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🎮", "Game Boards", "Chess, Sudoku, and grid games represented as 2D matrices"),
                        new Topic.UseCase("🤖", "Machine Learning", "Neural network weights stored and multiplied as matrices"),
                        new Topic.UseCase("🖼️", "Image Processing", "Images are matrices of RGB pixel values")
                ))
                .withInterviewQuestions(
                        List.of("How do you rotate a matrix 90 degrees in-place?", "Search in a row-wise and column-wise sorted matrix.", "Find the longest path in a matrix using DFS."),
                        List.of("Medium", "Medium", "Hard")
                ));

        list.add(new Topic("dictionaries", "Dictionaries", "Data Structures",
                "Key-value data structures and hash maps", "Dictionaries.mp4", "#162447", "📖",
                new String[]{"dict", "hashmap", "key-value"})
                .withComplexity("O(1)", "O(1)", "O(n)",
                        "Lookup by key", "Average operations", "Worst case with collisions",
                        "O(n)", "Stores n key-value pairs")
                .withCode(
                        "DICT OPERATIONS:\n  d[key] = val   → insert/update O(1)\n  d[key]         → lookup        O(1)\n  del d[key]     → delete        O(1)\n  key in d       → membership    O(1)\n  d.keys()       → all keys      O(n)\n  d.items()      → all pairs     O(n)",
                        "# Python Dictionary\nstudent = {'name': 'Alice', 'grade': 'A', 'age': 20}\n\n# Access\nprint(student['name'])  # Alice\n\n# Safe access\nprint(student.get('gpa', 0.0))  # 0.0 default\n\n# Update\nstudent['age'] = 21\n\n# Iterate\nfor key, val in student.items():\n    print(f'{key}: {val}')\n\n# Dict comprehension\nsquares = {x: x**2 for x in range(5)}\nprint(squares)  # {0:0, 1:1, 2:4, 3:9, 4:16}"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("⚙️", "Configurations", "App settings stored as key-value pairs (JSON/YAML/ENV)"),
                        new Topic.UseCase("📊", "Data Aggregation", "Group and count data by category instantly"),
                        new Topic.UseCase("🌐", "API Responses", "REST APIs return JSON — essentially nested dictionaries")
                ))
                .withInterviewQuestions(
                        List.of("How does Python's dict handle collisions?", "Implement an LRU cache using OrderedDict.", "Group anagrams together using a dictionary."),
                        List.of("Medium", "Hard", "Medium")
                ));

        list.add(new Topic("maps", "Maps", "Data Structures",
                "Map data structures and their usage", "MAPS.mp4", "#16213e", "🗺️",
                new String[]{"map", "key-value", "python"})
                .withComplexity("O(1)", "O(1)", "O(n)",
                        "Hash map access", "Average operations", "Worst case hash collisions",
                        "O(n)", "n mappings stored")
                .withCode("MAP OPERATIONS:\n  put(key, val)  → O(1)\n  get(key)       → O(1)\n  remove(key)    → O(1)\n  contains(key)  → O(1)",
                        "from collections import OrderedDict, defaultdict\n\n# defaultdict avoids KeyError\nword_count = defaultdict(int)\nfor word in 'the quick brown fox'.split():\n    word_count[word] += 1\n\n# OrderedDict preserves insertion order\nordered = OrderedDict()\nordered['first'] = 1\nordered['second'] = 2\n\n# Counter map\nfrom collections import Counter\nc = Counter('banana')\nprint(c)  # Counter({'a': 3, 'n': 2, 'b': 1})"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("🔄", "Caching / Memoization", "Map previously computed results to avoid recomputation"),
                        new Topic.UseCase("📍", "Coordinate Maps", "Geographic data mapped by location key"),
                        new Topic.UseCase("👤", "User Sessions", "Session tokens mapped to user data server-side")
                ))
                .withInterviewQuestions(
                        List.of("Difference between HashMap and TreeMap?", "How to implement a frequency map?", "Design a time-based key-value store."),
                        List.of("Easy", "Easy", "Hard")
                ));

        list.add(new Topic("tuple", "Tuple", "Data Structures",
                "Immutable ordered sequence in Python", "tuple.mp4", "#1f4068", "📦",
                new String[]{"tuple", "immutable", "python"})
                .withComplexity("O(1)", "O(n)", "O(n)",
                        "Index access", "Search / iteration", "Full traversal",
                        "O(n)", "Fixed-size immutable sequence")
                .withCode(
                        "TUPLE OPERATIONS:\n  t[i]       → access    O(1)\n  t.index(x) → find x   O(n)\n  t.count(x) → count x  O(n)\n  len(t)     → size     O(1)\n  a, b = t   → unpack   O(n)\n  NOTE: Cannot modify — immutable!",
                        "# Tuple creation\ncoords = (10, 20)\nrgb = (255, 128, 0)\n\n# Unpacking\nx, y = coords\nprint(x, y)  # 10 20\n\n# Tuple in function return\ndef min_max(lst):\n    return min(lst), max(lst)\n\nlo, hi = min_max([3, 1, 4, 1, 5, 9])\nprint(lo, hi)  # 1 9\n\n# Named tuple\nfrom collections import namedtuple\nPoint = namedtuple('Point', ['x', 'y'])\np = Point(3, 4)\nprint(p.x, p.y)  # 3 4"
                )
                .withUseCases(List.of(
                        new Topic.UseCase("📍", "Coordinates", "GPS points, pixel positions — unchanging pairs of values"),
                        new Topic.UseCase("🔑", "Dict Keys", "Tuples can be dict keys (lists cannot) due to immutability"),
                        new Topic.UseCase("📦", "Multiple Return", "Functions returning multiple values use tuples")
                ))
                .withInterviewQuestions(
                        List.of("Why are tuples hashable but lists are not?", "When would you use a tuple over a list?", "What is a named tuple and when is it useful?"),
                        List.of("Easy", "Easy", "Medium")
                ));

        return list;
    }

    @GetMapping("/")
    public String index(Model model) {
        Map<String, List<Topic>> grouped = new LinkedHashMap<>();
        // Consistent category ordering
        String[] categoryOrder = {"Data Structures", "Sorting", "Searching", "Graph Theory"};
        for (String cat : categoryOrder) {
            List<Topic> inCat = topics.stream()
                    .filter(t -> t.getCategory().equals(cat))
                    .collect(Collectors.toList());
            if (!inCat.isEmpty()) grouped.put(cat, inCat);
        }
        model.addAttribute("topics", topics);
        model.addAttribute("groupedTopics", grouped);
        model.addAttribute("categories", grouped.keySet());
        return "index";
    }

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable String id, Model model) {
        int idx = -1;
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getId().equals(id)) { idx = i; break; }
        }
        if (idx == -1) return "redirect:/";

        Topic topic = topics.get(idx);
        model.addAttribute("topic", topic);

        // Prev / Next
        model.addAttribute("prevTopic", idx > 0 ? topics.get(idx - 1) : null);
        model.addAttribute("nextTopic", idx < topics.size() - 1 ? topics.get(idx + 1) : null);

        // All topics in same category for sidebar
        List<Topic> allInCategory = topics.stream()
                .filter(t -> t.getCategory().equals(topic.getCategory()))
                .collect(Collectors.toList());
        model.addAttribute("allInCategory", allInCategory);

        // Related (same category, not self, max 4)
        List<Topic> related = allInCategory.stream()
                .filter(t -> !t.getId().equals(id))
                .limit(4)
                .collect(Collectors.toList());
        model.addAttribute("related", related);

        return "topic";
    }
}