## _spray_ Public API prototype

As an example and prototype, this sub-project simple uses spray-can, spray-client, and spray-routing
to hit the backend zensey api.

Follow these steps to run this api:

1. Launch SBT:

        $ sbt

1.5. (optional) Make intellij project:

        > gen-idea

2. Start the application:

        > re-start

3. Browse to http://localhost:8088/getUserGoals/{username}

4. Stop the application:

        > re-stop

As easy as that

if you want to view the api in intellij, assuming you've run the optional command gen-idea, then you can
open the source as a new project in intellij
