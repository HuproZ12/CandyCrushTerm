class CC extends Program{

    final char B = 'B';
    final char O = 'O';
    final char J = 'J';
    final char V = 'V';
    final char R = 'R';
    final char S = ' ';

    char[] bonbon = new char[]{'B', 'O', 'J', 'V', 'R'};
    char[][] plateau = new char[5][8];

    int score = 0;
    int tour = 0;
	
    void algorithm(){
	initialisation(plateau);
	println();
	toString(plateau);
	println();
	while((score < 1000) && (tour < 20)){
	    echangerCases(plateau);
	    tour = tour + 1;
	    while(combo(plateau)){
		supprimerBonbons(plateau);
		score = score + score(plateau);
		gravite(plateau);
		ajouterBonbons(plateau);
	    }
	    println();
	    toString(plateau);
	    println();
	    println("Score : " + score);
	    println("Tour : " + tour);
	    println();
	}
    }
    

    void initialisation(char[][] plateau){
	double random;
	for(int l = 0; l < length(plateau, 1); l++){
	    for(int c = 0; c < length(plateau, 2); c++){
		random = random() * 5;
		plateau[l][c] = bonbon[(int)random];
	    }
	}
    }

    void toString(char[][] plateau){
	for(int l = 0; l <= length(plateau, 1); l++){
	    if(l == 0){
		print("  ");
	    }
	    else{
		print((char)(64 + l) + "|");
	    }
	    for(int c = 0; c < length(plateau, 2); c++){
		if(l == 0){
		    print(c + 1);
		}
		else{
		    char car = plateau[l - 1][c];
		    if(car == B){
			print(ANSI_BLUE_BG + B + ANSI_BG_DEFAULT_COLOR);
		    }
		    else if(car == J){
			print(ANSI_YELLOW_BG + J + ANSI_BG_DEFAULT_COLOR);
		    }
		    else if(car == V){
			print(ANSI_GREEN_BG + V + ANSI_BG_DEFAULT_COLOR);
		    }
		    else if(car == R){
			print(ANSI_RED_BG + R + ANSI_BG_DEFAULT_COLOR);
		    }
		    else{
			print(car);
		    }
		}
	    }
	    println();
	}
    }

    boolean casesAdjacentes(String cases){
	boolean sortie = false;
	int maxl = max((int)charAt(cases, 0), (int)charAt(cases, 2));
	int minl = min((int)charAt(cases, 0), (int)charAt(cases, 2));
	int maxc = max((int)charAt(cases, 1), (int)charAt(cases, 3));
	int minc = min((int)charAt(cases, 1), (int)charAt(cases, 3));
	if(maxl - minl == 1){
	    if(maxc - minc == 0){
		sortie = true;
	    }
	}
	else if(maxl - minl == 0){
	    if(maxc - minc == 1){
		sortie = true;
	    }
	}
	return sortie;
    }

    boolean peutEchanger(String chaine, char[][] plateau){
	boolean sortie = true;
	int idx = 0;
	char car;

	if(length(chaine) != 4){
	    sortie = false;
	}
	else{
	    while((sortie) && (idx < 4)){
		car = charAt(chaine, idx);
		if(idx % 2 == 0){
		    if((int)(car - 65) >= length(plateau, 1)){
			sortie = false;
		    }
		}
		else{
		    if((car - '0') > length(plateau, 2)){
			sortie = false;
		    }
		}
		idx = idx + 1;
	    }
	}

	if(sortie){
	    sortie = casesAdjacentes(chaine);
	}

	return sortie;
    }

    String lectureEchangeCoordonnees(char[][] plateau){
	String entree = "";
	do{
	    print("Saisissez les coordonnées des cases que vous voulez échanger : ");
	    entree = readString();
	}while(!peutEchanger(entree, plateau));
	return entree;
    }

    char getBonbon(char[][] plateau, int l, int c){
	char sortie = '-';

	if(l < length(plateau, 1)){
	    if(c < length(plateau, 2)){
		sortie = plateau[l][c];
	    }
	}

	return sortie;
    }

    int compteSuivantDirection(char[][] plateau, int l, int c, int dirl, int dirc, char type){
	int sortie = 1;
	boolean equals = true;
	char car;
	int ligne = dirl;
	int colonne = dirc;

	while(equals && (l + ligne >= 0) && (c + colonne >= 0)){
	    car = getBonbon(plateau, l + ligne, c + colonne);
	    if(car == type){
		sortie = sortie + 1;
		ligne = ligne + dirl;
		colonne = colonne + dirc;
	    }
	    else{
		equals = false;
	    }
	}
	return sortie;
    }
    
    boolean[][] bonbonsASupprimer(char[][] plateau){
	boolean sortie[][] = new boolean[length(plateau, 1)][length(plateau, 2)];
	for(int l = 0; l < length(plateau, 1); l++){
	    for(int c = 0; c < length(plateau, 2); c++){
		if(compteSuivantDirection(plateau, l, c, 1, 0, plateau[l][c]) >= 3){
		    for(int i = 0; i < compteSuivantDirection(plateau, l, c, 1, 0, plateau[l][c]); i++){
			sortie[l + i][c] = true;
		    }
		}
		if(compteSuivantDirection(plateau, l, c, 0, 1, plateau[l][c]) >= 3){
		    for(int j = 0; j < compteSuivantDirection(plateau, l, c, 0, 1, plateau[l][c]); j++){
			sortie[l][c + j] = true;
		    }
		}
	    }
	}				        
	return sortie;
    }

    char[][] supprimerBonbons(char[][] plateau){
	boolean[][] plat = bonbonsASupprimer(plateau);
	for(int l = 0; l < length(plateau, 1); l++){
	    for(int c = 0; c < length(plateau, 2); c++){
	        if(plat[l][c]){
		    plateau[l][c] = S;
		}		
	    }
	}
	return plateau;
    }

    void echangerCases(char[][] plateau){
	String entree = lectureEchangeCoordonnees(plateau);
        int l1 = (int)charAt(entree,0) - 65;
        int c1 = (int)charAt(entree,1) - 49;
        int l2 = (int)charAt(entree,2) - 65;
        int c2 = (int)charAt(entree,3) - 49;
        char temp = plateau[l1][c1];
        plateau[l1][c1] = plateau[l2][c2];
        plateau[l2][c2] = temp;
    }

    boolean combo(char[][] plateau){
	boolean sortie = false;
	int taille = length(plateau, 1) * length(plateau, 2);
	int compteur = 0;
	int l = 0;
	int c = 0;
	boolean[][] tab = bonbonsASupprimer(plateau);
	while((!sortie) && (compteur < taille)){
	    if(c == length(plateau, 2)){
		c = 0;
		l = l + 1;
	    }
	    if(tab[l][c]){
		sortie = true;
	    }
	    c = c + 1;
	    compteur = compteur + 1;
	}
	return sortie;
    }

    void gravite(char[][] plateau){
	int n;
        for(int l = length(plateau, 1) - 1; l > 0; l--){
            for(int c = 0; c < length(plateau, 2); c++){
                if(plateau[l][c] == S){
                    n = 1;
                    while((plateau[l - n][c] == S) && ((l - n) > 0)){
                        n = n + 1;
                    }
                    plateau[l][c] = plateau[l - n][c];
                    plateau[l - n][c] = S;
                }
            }
        }
    }

    void ajouterBonbons(char[][] plateau){
	double random;
	for(int l = 0; l < length(plateau, 1); l++){
	    for(int c = 0; c < length(plateau, 2); c++){
		if(plateau[l][c] == S){
		    random = random() * 5;
		    plateau[l][c] = bonbon[(int)random];
		}
	    }
	}
    }

    int score(char[][] plateau){
	int score = 0;
	for(int l = 0; l < length(plateau, 1); l++){
	    for(int c = 0; c < length(plateau, 2); c++){
		if(plateau[l][c] == S){
		    score = score + 10;
		}
	    }
	}
	return score;
    }
}
