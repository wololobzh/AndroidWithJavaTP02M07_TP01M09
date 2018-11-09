# AndroidWithJavaTP02M07_TP01M09
Retrofit + RecyclerView + AsyncTask + Menu + Room

# Rappel sur l'attribut android:lauchMode

## Standard

C'est le mode par défaut.

Le comportement de l'activité définie sur ce mode est une nouvelle activité qui sera toujours créée pour fonctionner séparément avec chaque intention envoyée. Imaginez, si 10 intentions sont envoyées pour composer un email, il devrait y avoir 10 activités lancées pour servir chaque intention séparément. En conséquence, il pourrait y avoir un nombre illimité de ce type d'activité lancé dans un appareil.

## singleTop

Le mode suivant est singleTop. Il est presque identique à celui standard, ce qui signifie que l'instance SingleTop Activity peut être créée autant que nous le souhaitons. La seule différence est que s'il existe déjà une instance Activity avec le même type en haut de la pile dans la tâche Caller, aucune nouvelle activité ne sera créée, mais une intention sera envoyée à une instance Activity existante via la méthode onNewIntent ().

En mode SingleTop, vous devez gérer un Intent entrant dans onCreate () et onNewIntent () pour le faire fonctionner dans tous les cas.

Un exemple de cas d'utilisation de ce mode est une fonction de recherche. Pensons à la création d'un champ de recherche qui vous mènera à une SearchActivity pour voir le résultat de la recherche. Pour une meilleure UX, nous mettons toujours un champ de recherche dans la page de résultats de recherche pour permettre à l’utilisateur de faire une autre recherche sans appuyer sur.

Imaginez maintenant, si nous lançons toujours une nouvelle SearchActivity pour servir de nouveaux résultats de recherche, 10 nouvelles activités pour 10 recherches. Ce serait extrêmement étrange lorsque vous appuyez sur cette touche car vous devez appuyer 10 fois de plus pour passer les résultats de la recherche. Activités pour revenir à votre activité racine.

Au lieu de cela, s'il y a SearchActivity en haut de la pile, il est préférable d'envoyer une Intention à une instance d'Activité existante et de la laisser mettre à jour le résultat de la recherche. Maintenant, il n'y aura qu'une seule SearchActivity placée en haut de la pile et vous pourrez simplement appuyer sur le bouton juste une fois pour revenir à l'activité précédente. Cela fait beaucoup plus de sens maintenant.

En tout cas, singleTop fonctionne avec la même tâche que l'appelant uniquement. Si vous vous attendez à ce qu'une intention soit envoyée à une activité existante placée au-dessus d'une autre tâche, je dois vous décevoir en disant que cela ne fonctionne pas de cette façon. Si Intent est envoyé depuis une autre application vers une activité unique, une nouvelle activité sera lancée dans le même aspect que la méthode launchMode standard (pré-Lollipop: placée au-dessus de la tâche de l'appelant, Lollipop: une nouvelle tâche sera créée).

## singleTask

Ce mode est assez différent du standard et du singleTop. Une Activity avec singleTask launchMode ne peut avoir qu'une seule instance dans le système (par exemple, Singleton). S'il existe une instance d'activité existante dans le système, l'intégralité de la tâche Hold contenue dans l'instance serait déplacée vers le haut tandis que Intent serait diffusée via la méthode onNewIntent (). Sinon, une nouvelle activité serait créée et placée dans la tâche appropriée.

S'il n'y a pas d'instance SingleTask Activity existante dans le système, une nouvelle instance sera créée et simplement placée au-dessus de la pile dans la même tâche.

Mais s'il existe une activité, toutes les activités placées au-dessus de cette activité de tâche unique seraient automatiquement et cruellement détruites de la manière appropriée (cycle de vie déclenché) pour qu'une activité que nous voulons voir apparaisse au-dessus de la pile. Dans le même temps, un Intent serait envoyé à l'activité SingleTask par le biais de la méthode onNewIntent ().

## singleInstance

Ce mode est assez proche de singleTask, seule une seule instance d'Activité pourrait exister dans le système. La différence est que la tenue de tâche que cette activité ne peut avoir qu’une seule activité, l’instance unique. Si une autre activité est appelée à partir de ce type d'activité, une nouvelle tâche sera automatiquement créée pour placer cette nouvelle activité. De la même manière, si l'activité SingleInstance est appelée, une nouvelle tâche sera créée pour placer l'activité.

De toute façon, le résultat est assez étrange. D'après les informations fournies par dumpsys, il semble qu'il y ait deux tâches dans le système, mais une seule est apparue dans le Gestionnaire des tâches dont la dernière est déplacée vers le haut. Par conséquent, bien qu’il existe une tâche qui fonctionne toujours en arrière-plan, mais que nous ne pouvions pas la rétablir au premier plan. Cela n'a aucun sens.

C'est ce qui s'est produit lorsque SingleInstance Activity est appelée alors qu'il existe déjà une activité dans la pile.

## Intent Flags

En plus d'affecter le mode de lancement directement dans AndroidManifest.xml, nous pouvons également attribuer plus de comportement à l'aide de l'objet appelé Intent Flags, par exemple:

Intent intent = new Intent(StandardActivity.this, StandardActivity.class);
intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
startActivity(intent);

Vous pouvez jouer avec beaucoup de drapeaux.



