
# Algorithm:
# Take a beginner, match it to closest intermediate (based on similarity),
# then match those to the first advanced that has all the required skills and lowest score,
# and then match to guru with all the required skills and lowest score

import json,httplib

#Get user-list
connection = httplib.HTTPSConnection('api.parse.com', 443)
connection.connect()
connection.request('GET', '/1/users', '', {
       "X-Parse-Application-Id": "ooPJOqMCb7HjxeNAueIE4gqPEmolGTzH2MRLO6UY",
       "X-Parse-REST-API-Key": "bqVlgYnq6RN3BGJvya83deaoIFKi3mEu3sIbOAqM"
     })
result = json.loads(connection.getresponse().read())
print result

newbies = []
inters = []
gurus = []
for person in result["results"]:
    if person["level"] == "newbie":
        newbies.append(person)
    elif person["level"] == "intermediate":
        inters.append(person)
    else:
        gurus.append(person)
print newbies
print inters
print gurus

#Make teams
teams = []
for noob in newbies:
    maxclose = 0
    maxindex = -1
    index = 0
    score = 0
    for inter in inters:
        if noob["ios"] and inter["ios"]:
            score += 1
        else:
            score -= 0.5
        if noob["android"] and inter["android"]:
            score += 1
        else:
            score -= 0.5
        if noob["web"] and inter["web"]:
            score += 1
        if noob["hardware"] and inter["hardware"]:
            score += 1
        if score>maxclose:
            maxclose = score
            maxindex = index
        index += 1
    team = []
    if maxindex>-1:
        team.append(noob)
        team.append(inters[maxindex])
        inters.pop(maxindex)
        #otherwise, this newbie won't be matched
        teams.append(team)
for t in teams:
    maxclose = 0
    maxindex = -1
    index = 0
    score = 0
    for inter in inters:
        if t[1]["ios"] and inter["ios"]:
            score += 1
        else:
            score -= 0.5
        if t[1]["android"] and inter["android"]:
            score += 1
        else:
            score -= 0.5
        if t[1]["web"] and inter["web"]:
            score += 1
        if t[1]["hardware"] and inter["hardware"]:
            score += 1
        if score>maxclose:
            maxclose = score
            maxindex = index
        index += 1
    if maxindex>-1:
        t.append(inters[maxindex])
        inters.pop(maxindex)
    else:
        t.append({"username":""})
for t in teams:
    maxclose = 0
    maxindex = -1
    index = 0
    score = 0
    for guru in gurus:
        if t[2]["ios"] and guru["ios"]:
            score += 1
        else:
            score -= 0.5
        if t[2]["android"] and guru["android"]:
            score += 1
        else:
            score -= 0.5
        if t[2]["web"] and guru["web"]:
            score += 1
        if t[2]["hardware"] and guru["hardware"]:
            score += 1
        if score>maxclose:
            maxclose = score
            maxindex = index
        index += 1
    if maxindex>-1:
        t.append(gurus[maxindex])
        gurus.pop(maxindex)
    else:
        t.append({"username":""})
for t in range(len(teams)):
    print "Team: ", (t+1), teams[t], "\n"

#update parse teams

    connection = httplib.HTTPSConnection('api.parse.com', 443)
    connection.connect()
    connection.request('POST', '/1/classes/Team', json.dumps({
       #"objectId": str(t),
       "P1": teams[t][0]["username"],
       "P2": teams[t][1]["username"],
       "P3": teams[t][2]["username"],
        "Guru":teams[t][3]["username"]
     }), {
       "X-Parse-Application-Id": "ooPJOqMCb7HjxeNAueIE4gqPEmolGTzH2MRLO6UY",
       "X-Parse-REST-API-Key": "bqVlgYnq6RN3BGJvya83deaoIFKi3mEu3sIbOAqM",
       "Content-Type": "application/json"
     })
    results = json.loads(connection.getresponse().read())
    print results

