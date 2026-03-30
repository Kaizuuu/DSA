package com.example.website.controller;

import com.example.website.model.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final List<Topic> topics = Arrays.asList(
        new Topic("list", "List", "Data Structures", "Learn about Python lists and their operations", "_List.mp4", "#1a1a2e", "📋", new String[]{"list", "python", "basics"}),
        new Topic("array", "Array", "Data Structures", "Understanding arrays and indexing", "ARRAY.mp4", "#16213e", "🔢", new String[]{"array", "index", "basics"}),
        new Topic("binary-search", "Binary Search", "Searching", "Efficient searching in sorted arrays", "binary Search.mp4", "#0f3460", "🔍", new String[]{"search", "binary", "O(log n)"}),
        new Topic("bubble-sort", "Bubble Sort", "Sorting", "Classic comparison-based sorting algorithm", "BUBBLE SORT.mp4", "#533483", "🫧", new String[]{"sort", "comparison", "O(n²)"}),
        new Topic("linear-search", "Linear Search", "Searching", "Simple sequential search algorithm", "Copy of Linear Search.mp4", "#2c003e", "📡", new String[]{"search", "linear", "O(n)"}),
        new Topic("matrix-tuple", "Matrix", "Data Structures", "Working with matrices and tuples in Python", "Copy of MATRIX TUPLE.mp4", "#1b1b2f", "🔲", new String[]{"matrix", "tuple", "python"}),
        new Topic("dictionaries", "Dictionaries", "Data Structures", "Key-value data structures and hash maps", "Dictionaries.mp4", "#162447", "📖", new String[]{"dict", "hashmap", "key-value"}),
        new Topic("graphs", "Graphs", "Graph Theory", "Graph data structures and traversal algorithms", "Graphs.mp4", "#1f4068", "🕸️", new String[]{"graph", "BFS", "DFS"}),
        new Topic("hash-table", "Hash Table", "Data Structures", "Efficient data storage with hash functions", "Hash Table.mp4", "#1b262c", "🗂️", new String[]{"hash", "O(1)", "collision"}),
        new Topic("heaps", "Heaps", "Data Structures", "Priority queues and heap data structure", "Heaps.mp4", "#0d0d0d", "🏔️", new String[]{"heap", "priority queue", "O(log n)"}),
        new Topic("insertion-sort", "Insertion Sort", "Sorting", "Building sorted array one element at a time", "Insertion sort.mp4", "#2d132c", "📥", new String[]{"sort", "insertion", "O(n²)"}),
        new Topic("linked-list", "Linked Lists", "Data Structures", "Dynamic linear data structure with nodes", "Linked lists in Python.mp4", "#1a1a2e", "🔗", new String[]{"linked list", "nodes", "pointer"}),
        new Topic("maps", "Maps", "Data Structures", "Map data structures and their usage", "MAPS.mp4", "#16213e", "🗺️", new String[]{"map", "key-value", "python"}),
        new Topic("queue", "Queue", "Data Structures", "First-In-First-Out data structure", "QUEUE.mp4", "#0f3460", "🚶", new String[]{"queue", "FIFO", "enqueue"}),
        new Topic("selection-sort", "Selection Sort", "Sorting", "Repeatedly finds the minimum element", "Selection sort.mp4", "#533483", "✅", new String[]{"sort", "selection", "O(n²)"}),
        new Topic("stacks", "Stacks", "Data Structures", "Last-In-First-Out data structure", "Stacks.mp4", "#1b1b2f", "📚", new String[]{"stack", "LIFO", "push/pop"}),
        new Topic("tree", "Tree", "Data Structures", "Hierarchical non-linear data structure", "tree.mp4", "#162447", "🌳", new String[]{"tree", "binary tree", "traversal"}),
        new Topic("tuple", "Tuple", "Data Structures", "Immutable ordered sequence in Python", "tuple.mp4", "#1f4068", "📦", new String[]{"tuple", "immutable", "python"})
    );

    @GetMapping("/")
    public String index(Model model) {
        Map<String, List<Topic>> grouped = topics.stream()
            .collect(Collectors.groupingBy(Topic::getCategory));
        model.addAttribute("topics", topics);
        model.addAttribute("groupedTopics", grouped);
        model.addAttribute("categories", grouped.keySet());
        return "index";
    }

    @GetMapping("/topic/{id}")
    public String topic(@PathVariable String id, Model model) {
        Topic topic = topics.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElse(null);
        if (topic == null) return "redirect:/";
        model.addAttribute("topic", topic);
        List<Topic> related = topics.stream()
            .filter(t -> !t.getId().equals(id) && t.getCategory().equals(topic.getCategory()))
            .limit(4)
            .collect(Collectors.toList());
        model.addAttribute("related", related);
        return "topic";
    }
}
