# ------------------------------------------------------------
# - La programmation orientee aspect pour Java/J2EE          -
# - Renaud Pawlak, Jean-Philippe Retaille, Lionel Seinturier -
# - Editions Eyrolles                                        -
# -                                                          -
# - Partie II  : Les outils de la POA                        -
# - Chapitre 4 : JAC                                         -
# ------------------------------------------------------------

# --------------------------------------------
# - Application: Gestion de commandes        -
# - Aspect:      Trace de la methode addItem -
# - Descripteur d'application                -
# --------------------------------------------

# L'identifiant de l'application
applicationName: Gestion de commandes

# Classe contenant la methode main de lancement de l'application
launchingClass: aop.jac.Customer

# Le(es) composant(s) d'aspect tisse(s)
# Ici seul le composant d'aspect traceid
# dont le fichier de configuration est traceaspect.acc
# est tisse
aspects: traceid traceaspect.acc true

# La definition du(des) composant(s) d'aspect
jac.acs: traceid aop.jac.TraceAspect
