FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="Ranim"
LABEL description="Super Mario Design Patterns – Fonctionne même si Main.java est à la racine de src/"

# Copie tout
COPY . /app
WORKDIR /app

# Compilation propre (gère les deux cas : Main dans src/ ou src/mariopatterns/)
RUN mkdir -p out && \
    find src -name "*.java" > sources.txt && \
    javac -d out @sources.txt && \
    rm sources.txt

# Vérifie que Main.class existe (dans out/ ou out/mariopatterns/)
RUN echo "Contenu du dossier out :" && ls -la out/ && \
    echo "Recherche de Main.class..." && \
    find out -name "Main.class" && \
    echo "CLASSE TROUVÉE !"

# Lancement du jeu (marche dans les deux cas)
ENTRYPOINT ["java", "-cp", "out", "Main"]
