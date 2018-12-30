import matplotlib.pyplot as plt
import numpy as np

b = open("best_log.txt", "r")
m = open("mean_log.txt", "r")

best = []
mean = []

while True:
    line = b.readline()
    if line in "\n": #vacia o solo salto de linea
        break
    best.append(float(line))
    mean.append(float(m.readline()))
m.close()
b.close()

# grafico mejores
plt.plot(np.array(range(len(best))), np.array(best))
axes = plt.gca()
axes.set_xlim([0, 20])
axes.set_ylim([0, max(best)])
plt.xlabel("Generaciones")
plt.ylabel("Mejor fitness")
plt.show()

# grafico promedios
plt.plot(np.array(range(len(mean))), np.array(mean))
axes = plt.gca()
axes.set_xlim([0, 20])
axes.set_ylim([0, max(best)])
plt.xlabel("Generaciones")
plt.ylabel("Fitness promedio")
plt.show()
