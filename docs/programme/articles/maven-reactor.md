The reactor is the part of Maven that allows it to execute a goal on a set of modules. As mentioned in the Maven 1.x 
([documentation on multi-modules builds](http://maven.apache.org/maven-1.x/using/multiproject.html)) 
(the reactor concept was already there in Maven 1.x), while modules are discrete unit of work, they can be gathered
 together using the reactor to build them simultaneously and:

The reactor determines the correct build order from the dependencies stated by each project in their respective project
 descriptors, and will then execute a stated set of goals. It can be used for both building projects and other goals,
  such as site generation.

As explained, the reactor is what makes multi-module builds possible: it computes the directed graph of dependencies
 between modules, derives the build order from this graph (that's why cyclic dependencies are disallowed, 
 which is good thing anyway) and then executes goals on the modules. In other words, a "multi-modules build" is a
  "reactor build" and a "reactor build" is a "multi-modules build".

In Maven 2.x, the support of multi-module builds has been very much improved and the reactor has become transparent 
to Maven users. But it's still there and is used under the hood.

In September 2008 (i.e. a long time after the rollout of Maven 2), 
a ([reactor plugin](http://maven.apache.org/plugins/maven-reactor-plugin/)) has been created to make it possible 
to interact (again) more closely with the Maven reactor. Brett Porter blogged about it 
in ([Reactor: My New Favourite Maven Plugin](http://brettporter.wordpress.com/2008/09/19/reactor-my-new-favourite-maven-plugin/)).

Most of the reactor plugin features are now natively supported (since Maven 2.1.0). 
([See Maven Tips and Tricks: Advanced Reactor Options](http://www.sonatype.com/people/2009/10/maven-tips-and-tricks-advanced-reactor-options/)).