from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn import tree
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
from sklearn.cluster import KMeans
import numpy as np

data = datasets.load_wine()

###################
#      EXO 1      # 
###################

# Chargement du dataset

features = data.data 
label = data.target #etiquette
# print(data.DESCR) #DESCR du dataset

# Split train et test (77 train et 99 random state)
X_train, X_test, Y_train, Y_test = train_test_split(features, label, test_size = 0.33, random_state=99)


###################
#      EXO 2      #
###################


# model = DecisionTreeClassifier(max_depth=3) #Arbre de decision profondeur 3

# model_learn = model.fit(X_train, Y_train) # Entrainement

# tree.plot_tree(model_learn) 

# plt.show()
# plt.savefig("figure.png") #Enregistrer img

# print(model_learn.score(features, label))


###################
#      EXO 3      #
###################

scaler = StandardScaler()

# X_train = scaler.fit_transform(X_train)
# X_test = scaler.transform(X_test) #scaling pour pretraitement

# model = DecisionTreeClassifier(max_depth=3) # Entrainement
# model_learn = model.fit(X_train, Y_train)

# tree.plot_tree(model_learn)
# plt.savefig("figureScale.png")


# print(model_learn.score(X_test, Y_test))

#Pas d'amelioration surtout utile pour KNN et l'autre


###################
#      EXO 3      #
###################


# pca = PCA(n_components=2)
# X_train_pca = pca.fit_transform(X_train)
# X_test_pca = pca.transform(X_test)


# model = DecisionTreeClassifier(max_depth=3)  # Entrainement
# model_learn = model.fit(X_train_pca, Y_train)

# tree.plot_tree(model_learn)
# plt.savefig("figurePCA.png")

# print(model_learn.score(X_test_pca, Y_test))

# #Ca ameliore pas, pire ca peut le downgrade car on perd des donnees

# print(pca.explained_variance_ratio_)        # variance de chaque composante
# print(pca.explained_variance_ratio_.sum()) 

# #Pas satisfaisant, score de ~50%, on perds la moitie des infos

model = KMeans(n_clusters=4) # 4 cluster

model_train = model.fit(features) # Entrainement, supervised donc pas besoin de train split

# print(model.labels_) # cluster de chaque donnee
# print(model.cluster_centers_) # coor des centres

# plt.scatter(features[model.labels_ == 0, 0], features[model.labels_ == 0, 1], c='green', label='cluster 1')
# plt.scatter(features[model.labels_ == 1, 0], features[model.labels_ == 1, 1], c='yellow', label='cluster 2')
# plt.scatter(features[model.labels_ == 2, 0], features[model.labels_ == 2, 1], c='blue', label='cluster 3')
# plt.scatter(features[model.labels_ == 3, 0], features[model.labels_ == 3, 1], c='red', label='cluster 4')
# plt.legend()
# plt.savefig("figureKMeans.png")

# plt.scatter(features[:,0], features[:,1], c=model.labels_)
# plt.scatter(model.cluster_centers_[:,0], model.cluster_centers_[:,1],
#             s=300, marker='*', c='red')
# plt.savefig("figureKMeans.png")

premier_cluster = features[model.labels_ == 0] # valeurs attribus cluster 1
# print(premier_cluster)

premier_index = np.where(model.labels_ == 0)[0][0]
print(premier_index)

#Comme on split pas et qu'on a pas de Y pour comparer on utilise l'inertie


#Obtenir l'inertie 
wcss = [] 
for i in range(1, 11):
    model = KMeans(n_clusters=i)
    model.fit(features)
    wcss.append(model.inertia_)

plt.plot(range(1, 11), wcss)
plt.title('Elbow Method')
plt.xlabel('Number of clusters')
plt.ylabel('WCSS')
plt.savefig("elbow.png")