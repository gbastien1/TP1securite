package TP1Securite;

public class Feistel {

	public Feistel(String filecontent) {
		
	}
	
}

//http://www.commentcamarche.net/forum/affich-25066859-cryptography-algorithme-de-feistel-en-java-c
// Algo temporaire en C trouvé sur le net

char f(char caractere){	//f est une bijection aléatoire ou pseudo-aléatoire 
                                        // (définition de bijection sur wikipédia =P )
	return (char) ( 7*((int) caractere ) + 3 ) % 256 
            //cette formule est vraiment une bijection pourrie pour faire un cryptage  
            // correct mais tu peux la changer
}

void simpletour(char* iarray, char* oarray, int size){
	int cpt = 0;
	while(cpt<size){
		oarray[2*cpt] = iarray[2*cpt+1];
		oarray[2*cpt+1] = iarray[2*cpt] ^ f(iarray[2*cpt+1]);	
                    //le symbole ^ est le symbole de l'opération logique XOR en c
		cpt++;
	}
}		//la chaine de caractère finale se situe dans la chaine oarray

void multitour(char* iarray, char* oarray, int size, int nbtours){
	char* temp;
	while(nbtours > 0){
		simpletour(iarray, oarray, size);
		temp = iarray;
		iarray = oarray;
		oarray = temp;
		nbtours--;
	}	
}		//la chaine de caractère final se situe dans la chaine iarray


//Variables a déclarer dans la fonction main()

int size;		//moitié de la taille de la chaine de caractère d'entrée
char iarray[2n];	//chaine de caractère d'entrée

char oarray[2n];	//chaine de caractère de résultat

//Exemple d'utilisation (DES) :

multitour(iarray, oarray, size, 16);
//La chaine iarray contient maintenant une chaine cryptée