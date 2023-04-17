<div id="assignment_show" class="assignment content_underline_links">
    <!--Student View-->
    <div class="assignment-title">
      <div class="title-content">
        <h1 class="title">
          Group Project (REAL)
        </h1>
      </div>
      <div class="assignment-buttons">
<ul class="student-assignment-overview">
  <li>
    <span class="title">Due</span>
    <span class="value">
          <span class="date_text">
                <span class="display_date">May 7</span> by 
                <span class="display_time">11:59pm</span>
          </span><!--
        --></span>
  </li>
  <li>
    <span class="title">Points</span>
    <span class="value">30</span>
  </li>
    <li>
      <span class="title">Submitting</span>
      <span class="value">a file upload</span>
    </li>
      <li>
        <span class="title">File Types</span>
        <span class="value">zip</span>
      </li>


  <div class="clear"></div>
</ul>

  <div class="clear"></div>


  <div class="clear"></div>



  <div class="description user_content enhanced"><p><strong>Course code and name:&nbsp;</strong>COSC2658 Data Structures and Algorithms</p>
<p><strong>Assessment name:</strong> Group Project</p>
<p><strong>Length:</strong> Source Code</p>
<p><strong>Type:</strong> Group of 3 or 4</p>
<p><strong>Feedback mode:</strong>&nbsp;Written feedback</p>
<p><strong>Late work:</strong> For assignments 1 to 5 days late, a penalty of 10% (of the marks awarded) per day will apply. For assignments more than 5 days late, a penalty of 100% will apply. Weekend days (Saturday and Sunday) are counted when counting the total late days.</p>
<h2>Learning Objectives Assessed</h2>
<ul>
<li>CLO 1: Compare, contrast, and apply the key algorithmic design paradigms: brute force, divide and conquer, decrease and conquer, transform and conquer, greedy, dynamic programming.</li>
<li>CLO 2: Compare, contrast, and apply key data structures: trees, lists, stacks, queues, hash tables, and graph representations.</li>
<li>CLO 3: Define, compare, analyse, and solve general algorithmic problem types: sorting, searching, string processing, graphs, and geometric.</li>
<li>CLO 4: Compare, contrast, and apply algorithmic tradeoffs: time vs. space, deterministic vs. randomized, and exact vs. approximate.</li>
<li>CLO 5: Implement, empirically compare, and apply fundamental algorithms and data structures to real-world problems.</li>
</ul>
<h2>Ready for Life and Work</h2>
<ul>
<li>Completing this assignment will help you develop the skill to design and implement data structures and algorithms that solve a medium-to-big problem based on a given set of specifications. You will also develop the skill to evaluate the time efficiency of your design and implementation.</li>
</ul>
<hr>
<h2>Assessment Details</h2>
<p><span data-contrast="auto"><strong>Important: </strong>You are not allowed to use the classes/interfaces defined in the Java Collection Framework. You can use/extend the examples/solutions I shared on our course's GitHub page. You are not allowed to use code copied from the Internet.</span></p>
<p><span data-contrast="auto"><strong>Background:</strong> You found a box containing THE book "How to earn HDs for every course at RMIT with only 5-minute self-learning per day". It's very very very precious, isn't it? </span><span data-contrast="auto">However, to open the box you need to know a secret key that is used to unlock the box. By looking at the lock pattern, you know that the secret key contains 16 letters, each can be either "R", "M", "I" or "T". You can calculate how many combinations there are. If each try takes you one second, do you want to try?</span></p>
<p><span data-contrast="auto">Fortunately, you have a hack that: given a guessed secret key, it returns the number of positions that are matched between the guessed key and the correct key. For example (I used only 4 letters here for demonstration purposes only), if the correct secret key is "RMIT" and you guess "MMIT", the last three positions are correct, so 3 is returned. If you guess "TRMI", zero is returned. If you guess "RRRR", 1 is returned. And if you guess "RMIT", 4 is returned. Of course, in your case, you want to have 16 returned.</span></p>
<h3><span data-contrast="auto">Technical Description</span></h3>
<p>The correct secret key contains exactly 16 letters, each of which must be either "R", "M", "I", or "T". It is managed by a class SecretKey (which I will describe later).</p>
<p><span data-contrast="auto">You need to create a class SecretKeyGuesser. The class contains one required public method (you can add more private methods/attributes as needed):</span></p>
<pre><span data-contrast="auto">void start()</span></pre>
<p>In this start() method, your code must:</p>
<p>First, create a new SecretKey instance (I will provide a sample SecretKey implementation - you don't need to do anything)</p>
<p>Then, repeatedly call the guess() method of the SecretKey instance. For each call, you must provide a String argument containing exactly 16 letters. Each letter of the String argument can only be "R", "M", "I", or "T". If you provide an invalid argument, you will get -1 as the returned value. If your argument is valid, the guess() method will return a value from 0 to 16, denoting how many positions in your guessed value are matched with the correct secret key.</p>
<p>Your code must stop whenever it receives 16 from a call to the guess() method. Then, you must display the correct secret key.</p>
<p><a class="inline_disabled external" href="https://github.com/TriDang/cosc2658-2023-s1/tree/main/project" target="_blank" rel="noreferrer noopener"><span>A sample implementation is given here</span></a></p>
<p><strong>More information about testing your program</strong></p>
<p>The following steps will be used to test your program</p>
<ul>
<li>A secret key is generated (your program will NOT know anything about this key)</li>
<li>An instance of your SecretKeyGuesser class is created</li>
<li>The start() method of the SecretKeyGuesser instance will be called</li>
<li>Anytime your program calls the guess() method, a counter value is increased</li>
<li>Your program must try to make this counter as small as possible when it finds out the correct secret key</li>
<li>I will run your program with three test cases. Then, the three counter values are added together. This is the performance of your program (the smaller, the better)</li>
</ul>
<h3>Deliverables</h3>
<p>You need to provide me with the following outputs regarding the system you develop</p>
<ul>
<li>A README.txt file that describes the contribution score of all members (explained in the Contribution Score section below) and contains the link to the project video (explained in the Video Demonstration section below)</li>
<li>Source code: Java source code (no library included)</li>
<li>Technical report: a PDF document that contains the following sections
<ul>
<li>An overview and high-level design of your system (Java classes, methods, their relationships, software design patterns, etc.)</li>
<li>The data structures and algorithms you apply or develop (note: you are not restricted to the data structures and algorithms covered in this course; you can create your own data structures and algorithms). You need to describe in detail the working of your data structures and algorithms.</li>
<li>Complexity analysis: you need to provide the complexity analysis of the algorithms you proposed.</li>
<li>Evaluation: you need to describe how you evaluate the correctness and efficiency of your system experimentally.</li>
</ul>
</li>
<li>Video demonstration: create a short video (around 5 minutes and less than 8 minutes) to show a demo of your system. You have to upload your video to YouTube and present its URL in the REAME.txt file.</li>
</ul>
<h3>Contribution Score</h3>
<p>The starting score for each student is 3 points. Each group can decide to add/remove points to/from each member, but the total point of the whole group is kept unchanged (it is = (the number of members) * 3). Additional rules:</p>
<ul>
<li>The maximum point for a member is 5.</li>
<li>If a member gets zero points =&gt; that member gets zero for the whole group project assignment. In this case, the total point of the whole group = (the number of members whose scores &gt; zero) * 3.</li>
<li>The contribution score must be agreed upon by all members. If there are disagreements, you must inform the lecturer/coordinator before the due time.</li>
<li>The maximum score for the whole project is 30. If you get more than 30 (due to a high contribution score), the final score is 30.</li>
</ul>
<h3>Support Resources</h3>
<p>This assessment requires that you meet RMIT's expectations for academic integrity. More information and advice on how to avoid plagiarism are available in the Getting Started module.</p>
<p>Open&nbsp;<a href="https://rmit.instructure.com/courses/118548/pages/how-to-succeed-in-data-structures-and-algorithms#fragment-2" data-api-endpoint="https://rmit.instructure.com/api/v1/courses/118548/pages/how-to-succeed-in-data-structures-and-algorithms%23fragment-2" data-api-returntype="Page">the academic integrity page</a>.</p>
<p>Additional library and learning resources are available to help with the assessment in this course</p>
<p>Link to&nbsp;<a title="Assignment Support" href="https://rmit.instructure.com/courses/118548/pages/assignment-support" data-api-endpoint="https://rmit.instructure.com/api/v1/courses/118548/pages/assignment-support" data-api-returntype="Page">Assignment Support</a>.</p>
<h3>Submission Instructions</h3>
<ul>
<li>Java source code, PDF report, and README.txt file should be placed and submitted as a single zip file on Canvas</li>
</ul>
<h3>Rubric</h3>
<p>Please find the rubric below</p></div>


  <div style="display: none;">
    <span class="timestamp">1683478799</span>
    <span class="due_date_string">05/07/2023</span>
    <span class="due_time_string">11:59pm</span>
  </div>
</div>
</div>
</div>