# Optimal-Sequence-Aligner

This repository contains scripts which aim to tackle the problem of sequence alignment adjusted to avoid multiple consecutive gaps. This is achieved by using the same concepts from the Needleman-Wunch algorithm, however adapted to take into consideration an affine gap penalty. In the affine gap penalty, extending a gap is less costly than opening one, however, in this case, extending a gap should be considered as extremely undesirable if we want to avoid executive gaps. Hence, we can use the algorithm for optimal pairwise alignment that works under the affine gap penalty, but adjust it by setting the cost of a gap extension to -∞. An adaptation of the optimal sequence alignment under affine gap penalty constructed by Durbin et al. is used. 

## Initialization 

The algorithm uses dynamic programming to divide the large problem into smaller subproblems to determine the optimal solution. We begin with two sequences S and T which are represented in a matrix in the following way:

Let our two sequences be

S = A P P L E

T = H A P E

with a match score of 1, a mismatch score of -1 and a penalty score of -1

| | - | A | P | P | L | E |
| ---| ---| --- | --- | --- | ---| --- |
| **_** | | | | | | 
| **H** | | | | | |
| **A** | | | | | |
| **P** | | | | | |
| **E** | | | | | |

We begin initialization:

F(i, j) = Cell in the i'th row and the j'th position. Each cell contains three attributes: M, I<sub>x</sub>, I<sub>y</sub>.

F(0, 0) &larr; M = 0 <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>x</sub> = p <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>y</sub> = p

where p represents the penalty score

F(0, j) &larr; M = -&infin; <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>x</sub> = -&infin; <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>y</sub> = p if j = 1 or -&infin; if j > 1

F(i, 0) &larr; M = -&infin; <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>y</sub> = p if i = 1 or -&infin; if i > 1 <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>y</sub> = -&infin;

This would fill our example matrix in the following way:

| | - | A | P | P | L | E |
| ---| ---| --- | --- | --- | ---| --- |
| **_** | M = 0 <br> I<sub>x</sub> = -1 <br> I<sub>y</sub> = -1 | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -1  | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin;| M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin;
| **H** | M = -&infin; <br> I<sub>x</sub> = -1; <br> I<sub>y</sub> = -&infin; | | | | |
| **A** | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | | | | |
| **P** | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | | | | |
| **E** | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | | | | |

## Matrix Filling

We fill each remaining cell in the following way:

M(i, j) &larr; max = M(i-1, j-1) + s(s<sub>i</sub> t<sub>j</sub>) <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>x</sub>(i-1, j-1) + s(s<sub>i</sub> t<sub>j</sub>) <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; I<sub>y</sub>(i-1, j-1) + s(s<sub>i</sub> t<sub>j</sub>) 

I<sub>x</sub> &larr; M(i-1, j) + p <br>
I<sub>y</sub> &larr; M(i, j-1) + p

where s(s<sub>i</sub> t<sub>j</sub>) = mismatch score if s<sub>i</sub> &ne; t<sub>j</sub> or match score if s<sub>i</sub> = t<sub>j</sub> 

This would fill our example matrix in the following way:

| | - | A | P | P | L | E |
| ---| ---| --- | --- | --- | ---| --- |
| **_** | M = 0 <br> I<sub>x</sub> = -1 <br> I<sub>y</sub> = -1 | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -1  | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin;| M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin;
| **H** | M = -&infin; <br> I<sub>x</sub> = -1 <br> I<sub>y</sub> = -&infin; |  M = -1 <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -2 <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -2 | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -3 | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin;| M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin;
| **A** | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = 0 <br> I<sub>x</sub> = -2 <br> I<sub>y</sub> = -&infin;| M = -2 <br> I<sub>x</sub> = -3 <br> I<sub>y</sub> = -1 | M = -3 <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -3 | M = -4 <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -4 | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -5
| **P** | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -&infin; <br> I<sub>x</sub> = -1 <br> I<sub>y</sub> = -&infin; | M = 1 <br> I<sub>x</sub> = -3 <br> I<sub>y</sub> = -&infin; | M = 0 <br> I<sub>x</sub> = -4 <br> I<sub>y</sub> = 0 | M = -4 <br> I<sub>x</sub> = -5 <br> I<sub>y</sub> = -1| M = -5 <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -5
| **E** | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -&infin; <br> I<sub>x</sub> = -&infin; <br> I<sub>y</sub> = -&infin; | M = -2 <br> I<sub>x</sub> = 0 <br> I<sub>y</sub> = -&infin; | M = 0 <br> I<sub>x</sub> = -1 <br> I<sub>y</sub> = -3 | M = -1 <br> I<sub>x</sub> = -5 <br> I<sub>y</sub> = -1 | M = 0 <br> I<sub>x</sub> = -6 <br> I<sub>y</sub> = -2


## Traceback

Finally, using this matrix we can traceback to determine our optimal alignment. In our example matrix we would get the following alignment:

**- A P P L E**<br>
**H A - P - E**

Notice that in a classic Needleman-Wunch algorithm an alternative alignment could be:

**- A P P L E**<br>
**H A P - - E**

However, our algorithm does not detect this as an optimal solution since it has two consecutive gaps which is undesirable.

# Running Instructions

To run the algorithm, the MultiGapFree.java file must be run. In this file you must specify which the file which contains the two sequences you want to align. Examples of files in the correct format are also included. You can also alter the match, mismatch and penalty scores to your own discretion. 





