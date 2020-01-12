# Merkle tree
1) Dans l’illustration, imaginons que je possède le Merkle tree. Quelqu’un me donne le bloc de données L2 mais je ne lui fais pas confiance.
Comment puis-je vérifier si les données de L2 sont valides​ ​?

Caculer H(L2) du bloc donné.
Puis appliquer la fonction H(H(L2) + H(L1)) = H0
Puis appliquer la fonction H(H(0) + H(1)) = top hash
Vérifier que ce calcul mène bien à notre merkle root hash.

On peut donc prouver que le bloc donné L2 appartient à notre Merkle tree, en utilisant les hashs sans dévoiler les données.

2) Je possède le bloc L3 et le Merkle root. 
Par contre, je ne possède pas les autres blocs ni le Merkle tree.
Quelles informations dois-je obtenir au minimum pour m’assurer 
que le bloque L3 fait bien partie du Merkle tree 
qui a pour racine le Merkle root que je possède ?

J'ai besoin au minimum de H1-1 et de H0.

3) Quelles sont des exemples d’application pour un Merkle tree ? 

- Blockchain and bitcoins
- Vérifier l'intégrité des données dans des systèmes distribués où des mêmes données doivent exister à différents endroits :
	par exemple, détecter des incohérences entre des réplicas de base de données