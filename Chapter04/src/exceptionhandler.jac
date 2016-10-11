# ------------------------------------------------------------
# - La programmation orientee aspect pour Java/J2EE          -
# - Renaud Pawlak, Jean-Philippe Retaille, Lionel Seinturier -
# - Editions Eyrolles                                        -
# -                                                          -
# - Partie II  : Les outils de la POA                        -
# - Chapitre 4 : JAC                                         -
# ------------------------------------------------------------

# -----------------------------------------------------------
# - Application: Generateur d'exception                     -
# - Aspect:      Introduction d'un gestionnaire d'exception -
# - Descripteur d'application                               -
# -----------------------------------------------------------

# L'identifiant de l'application
applicationName: Generateur d'exception

# Classe contenant la methode main de lancement de l'application
launchingClass: aop.jac.GenerateExcept

# Le(es) composant(s) d'aspect tisse(s)
# Ici seul le composant d'aspect ehid
# dont le fichier de configuration est exceptionhandler.acc
# est tisse
aspects: ehid exceptionhandler.acc true

# La definition du(des) composant(s) d'aspect
jac.acs: ehid aop.jac.ExceptionHandlerAspect
