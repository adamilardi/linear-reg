print __doc__

import numpy as np
import pylab as pl

from sklearn.linear_model import SGDRegressor
from sklearn.datasets.samples_generator import make_regression

# this is our test set, it's just a straight line with some
# gaussian noise
X, Y = make_regression(n_samples=10, n_features=1, n_informative=1, random_state=0, noise=0)




X = np.array([[x] for x in xrange(100)])
Y = np.array([x for x in xrange(100)]) 

print X
print Y

# run the classifier
clf = SGDRegressor(alpha=0.000001, n_iter=1)
clf.fit(X, Y)

print 'coef', clf.coef_, clf.intercept_
# and plot the result
pl.scatter(X, Y, color='black')
pl.plot(X, clf.predict(X), color='blue', linewidth=3)
pl.show()



from sklearn import linear_model
clf = linear_model.LinearRegression()
clf.fit(X, Y)
print 'coef', clf.coef_, clf.intercept_
pl.scatter(X, Y, color='black')
pl.plot(X, clf.predict(X), color='blue', linewidth=3)
pl.show()