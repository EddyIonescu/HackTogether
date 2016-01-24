# HackTogether

This PennApps brought us Hacker Gurus, who act as mentors in a team. 

But not everyone got a guru, and not everyone ended up in a guru-led team that they liked. 

That's why we brought HackingTogether, an app that forms balanced teams of beginner and intermediate hackers as well as hacker gurus.

It also guarantees that all team-members want to make something on the same platform, which will lead to a more productive and engaging hackathon.

# How it works:

Hacker Gurus and Hackers interested in being part of a guru-led team can register using our simple, material-themed Android App (iOS and Web coming soon)

The data is stored in a cloud-based database using the Parse API

At a given point, a Python program that matches all the teams (by communicating with Parse via REST) runs and adds the teams as a seperate class into the database.

Now, the Android App will see that the teams have been formed and will thus show them - and prevent the user from changing their preferences.

The only information visible to team-members will be their names and the Devpost profile link of the Guru.



