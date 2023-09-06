# Optimal-Sequence-Aligner

This repository contains scripts which aim to tackle the problem of sequence alignment adjusted to avoid multiple consecutive gaps. This is achieved by using the same concepts from the Needleman-Wunch algorithm, however adapted to take into consideration an affine gap penalty. In the affine gap penalty, extending a gap is less costly than opening one, however, in this case, extending a gap should be considered as extremely undesirable if we want to avoid executive gaps. Hence, we can use the algorithm for optimal pairwise alignment that works under the affine gap penalty, but adjust it by setting the cost of a gap extension to -∞. An adaptation of the optimal sequence alignment under affine gap penalty constructed by Durbin et al. is used. 

## Initialization 

The algorithm uses dynamic programming to divide the large problem into smaller subproblems to determine the optimal solution. We begin with two sequences S and T which are represented in a matrix in the following way:

S = A P P L E

T = H A P E

| | A | P | P | L | E |
| ---| ---| --- | --- | --- | ---|
| | | | | | | 
| **H** | | | | | |
| **A** | | | | | |
| **P** | | | | | |
| **E** | | | | | |

We begin initialization:

F(i, j) = Cell in the i'th row and the j'th position. Each cell contains three attributes: M, Ix, Iy.



