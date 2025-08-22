// Populate the sidebar
//
// This is a script, and not included directly in the page, to control the total size of the book.
// The TOC contains an entry for each page, so if each page includes a copy of the TOC,
// the total size of the page becomes O(n**2).
class MDBookSidebarScrollbox extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        this.innerHTML = '<ol class="chapter"><li class="chapter-item expanded "><a href="introduction.html">Introduction</a></li><li class="chapter-item expanded "><a href="course-tools.html">Course Tools</a></li><li class="chapter-item expanded affix "><li class="part-title">Program Correctness</li><li class="chapter-item expanded "><a href="program-correctness/concrete-evaluation.html">Concrete Evaluation</a></li><li class="chapter-item expanded "><a href="program-correctness/propositions-and-proofs.html">Proposition and Proofs</a></li><li class="chapter-item expanded "><a href="program-correctness/symbolic-execution.html">Symbolic Execution</a></li><li class="chapter-item expanded "><a href="program-correctness/preconditions-and-proof-states.html">Preconditions and Proof States</a></li><li class="chapter-item expanded "><a href="program-correctness/inductive-reasoning.html">Inductive Reasoning</a></li><li class="chapter-item expanded "><a href="program-correctness/mathematical-induction.html">Mathematical Induction</a></li><li class="chapter-item expanded "><a href="program-correctness/variants-of-inductive-reasoning.html">Variants of Inductive Proof</a></li><li class="chapter-item expanded "><a href="program-correctness/propositional-logic.html">Propositional Logic</a></li><li class="chapter-item expanded "><a href="program-correctness/first-order-logic.html">First-order Logic</a></li><li class="chapter-item expanded "><a href="program-correctness/natural-deduction.html">Natural Deduction</a></li><li class="chapter-item expanded "><a href="program-correctness/additional-topics-in-logic.html">Additional Topics in Logic</a></li><li class="chapter-item expanded affix "><li class="part-title">Mathematical Modeling</li><li class="chapter-item expanded "><a href="mathematical-modeling/sets-and-their-operations.html">Sets and Their Operations</a></li><li class="chapter-item expanded "><a href="mathematical-modeling/set-inclusion-principles.html">Set Inclusion Principles</a></li><li class="chapter-item expanded "><a href="mathematical-modeling/contradictory-proof.html">Contradictory Proof</a></li><li class="chapter-item expanded "><a href="mathematical-modeling/functions-and-relations.html">Functions and Relations</a></li><li class="chapter-item expanded "><a href="mathematical-modeling/equivalences.html">Equivalences</a></li><li class="chapter-item expanded affix "><li class="part-title">Program Complexity</li><li class="chapter-item expanded "><a href="program-complexity/fundamental-counting-principles.html">Fundamental Counting Principles</a></li><li class="chapter-item expanded "><a href="program-complexity/ordered-choices.html">Ordered and Unordered Choice</a></li><li class="chapter-item expanded "><a href="program-complexity/counting-based-reasoning.html">Counting-based Reasoning</a></li><li class="chapter-item expanded "><a href="program-complexity/counting-operations.html">Counting Operations</a></li><li class="chapter-item expanded "><a href="program-complexity/recurrences.html">Recurrences</a></li><li class="chapter-item expanded affix "><li class="part-title">Mathematical Applications to Programming</li><li class="chapter-item expanded "><div>Modeling with Graphs</div></li><li><ol class="section"><li class="chapter-item expanded "><a href="modeling-with-graphs/introduction-to-graphs.html">Introduction to Graphs</a></li><li class="chapter-item expanded "><a href="modeling-with-graphs/minimum-spanning-trees.html">Minimum Spannning Trees</a></li><li class="chapter-item expanded "><a href="modeling-with-graphs/shortest-paths.html">Shortest Paths</a></li><li class="chapter-item expanded "><a href="modeling-with-graphs/case-study-automata.html">Case Study: Automata</a></li></ol></li><li class="chapter-item expanded "><div>Probabilistic Reasoning</div></li><li><ol class="section"><li class="chapter-item expanded "><a href="probabilistic-reasoning/frequentist-probability.html">Frequentist Probability</a></li><li class="chapter-item expanded "><a href="probabilistic-reasoning/random-variables-and-expectation.html">Random Variables and Expectation</a></li><li class="chapter-item expanded "><a href="probabilistic-reasoning/conditional-probability.html">Conditional Probabilities</a></li></ol></li></ol>';
        // Set the current, active page, and reveal it if it's hidden
        let current_page = document.location.href.toString().split("#")[0].split("?")[0];
        if (current_page.endsWith("/")) {
            current_page += "index.html";
        }
        var links = Array.prototype.slice.call(this.querySelectorAll("a"));
        var l = links.length;
        for (var i = 0; i < l; ++i) {
            var link = links[i];
            var href = link.getAttribute("href");
            if (href && !href.startsWith("#") && !/^(?:[a-z+]+:)?\/\//.test(href)) {
                link.href = path_to_root + href;
            }
            // The "index" page is supposed to alias the first chapter in the book.
            if (link.href === current_page || (i === 0 && path_to_root === "" && current_page.endsWith("/index.html"))) {
                link.classList.add("active");
                var parent = link.parentElement;
                if (parent && parent.classList.contains("chapter-item")) {
                    parent.classList.add("expanded");
                }
                while (parent) {
                    if (parent.tagName === "LI" && parent.previousElementSibling) {
                        if (parent.previousElementSibling.classList.contains("chapter-item")) {
                            parent.previousElementSibling.classList.add("expanded");
                        }
                    }
                    parent = parent.parentElement;
                }
            }
        }
        // Track and set sidebar scroll position
        this.addEventListener('click', function(e) {
            if (e.target.tagName === 'A') {
                sessionStorage.setItem('sidebar-scroll', this.scrollTop);
            }
        }, { passive: true });
        var sidebarScrollTop = sessionStorage.getItem('sidebar-scroll');
        sessionStorage.removeItem('sidebar-scroll');
        if (sidebarScrollTop) {
            // preserve sidebar scroll position when navigating via links within sidebar
            this.scrollTop = sidebarScrollTop;
        } else {
            // scroll sidebar to current active section when navigating via "next/previous chapter" buttons
            var activeSection = document.querySelector('#sidebar .active');
            if (activeSection) {
                activeSection.scrollIntoView({ block: 'center' });
            }
        }
        // Toggle buttons
        var sidebarAnchorToggles = document.querySelectorAll('#sidebar a.toggle');
        function toggleSection(ev) {
            ev.currentTarget.parentElement.classList.toggle('expanded');
        }
        Array.from(sidebarAnchorToggles).forEach(function (el) {
            el.addEventListener('click', toggleSection);
        });
    }
}
window.customElements.define("mdbook-sidebar-scrollbox", MDBookSidebarScrollbox);
