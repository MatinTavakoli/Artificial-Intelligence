import networkx as nx
import matplotlib.pyplot as plt
import time

def leftmost_digit(n):
    if n < 10:
        return n
    else:
        return leftmost_digit(int(n / 10))

def rightmost_digit(n):
    if n < 10:
        return n
    else:
        return rightmost_digit(int(n % 10))

def check_consistency(G, node, shape):
    free_neighbors = []
    for neighbor in list(G.neighbors(node)):
        if G.nodes[neighbor]['label'] == '':
            free_neighbors.append(neighbor)
    if len(free_neighbors) == 0:#check exact constraint
        if shape == '^' or shape == 's':
            res = 1 #multiplying
        elif shape == 'p' or shape == 'h':
            res = 0 #adding
        for neighbor in list(G.neighbors(node)):
            if shape == '^' or shape == 's':
                res *= G.nodes[neighbor]['label']
            elif shape == 'p' or shape == 'h':
                res += G.nodes[neighbor]['label']
        if shape == '^' or shape == 'p':
            if leftmost_digit(res) == G.nodes[node]['label']:
                print('all is good')
            else:
                print('value assigned to node is incosistent with it\'s neighbors')
                G.nodes[node]['domain'].remove(G.nodes[node]['label'])
                G.nodes[node]['label'] = ''#this means that the node must get another value!
        elif shape == 's' or shape == 'h': #checking rightmost
            if rightmost_digit(res) == G.nodes[node]['label']:
                print('all is good')
            else:
                print('value assigned to node is incosistent with it\'s neighbors')
                G.nodes[node]['domain'].remove(G.nodes[node]['label'])
                G.nodes[node]['label'] = ''#this means that the node must get another value!
    elif len(free_neighbors) == 1:#check domain of free neighbour
        free_neighbor_domain = []
        print('neigbors of', node, 'are', list(G.neighbors(node)))
        if shape == '^' or shape == 's':
            res = 1
        elif shape == 'p' or shape == 'h':
            res = 0
        for neighbor in list(G.neighbors(node)):
            if G.nodes[neighbor]['label'] != '':#if it isn't free
                print('shape of bounded neighbor is', G.nodes[neighbor]['shape'])
                print('label of bounded neighbor is', G.nodes[neighbor]['label'])
                #time.sleep(1)
                if shape == '^' or shape == 's':
                    res *= G.nodes[neighbor]['label']
                elif shape == 'p' or shape == 'h':
                    res += G.nodes[neighbor]['label']
        print('res without free neighbor is', res)
        free_neighbor = free_neighbors[0]
        for free_value in G.nodes[free_neighbor]['domain']:#checking domain of free neighbour
            if shape == '^' or shape == 's':
                res *= free_value
            elif shape == 'p' or shape == 'h':
                res += free_value
            print('res is', res)
            if shape == '^' or shape == 'p':
                print('leftmost digit of res is', leftmost_digit(res))
            elif shape == 's' or shape == 'h':
                print('rightmost digit of res is', rightmost_digit(res))
            print(node, 'node has value', G.nodes[node]['label'])
            if shape == '^' or shape == 'p':
                if leftmost_digit(res) == G.nodes[node]['label']:
                    print('value', free_value, 'suitable')
                    free_neighbor_domain.append(free_value)
                else:
                    print('value', free_value, 'not suitable')

            elif shape == 's' or shape == 'h':
                if rightmost_digit(res) == G.nodes[node]['label']:
                    print('value', free_value, 'suitable')
                    free_neighbor_domain.append(free_value)
                else:
                    print('value', free_value, 'not suitable')
            print(' ')
            if shape == '^' or shape == 's':
                res /= free_value
            elif shape == 'p' or shape == 'h':
                res -= free_value
        if(len(free_neighbor_domain) != 0):
            G.nodes[free_neighbor]['domain'] = free_neighbor_domain
            print('domain of', free_neighbor, ' changed to', G.nodes[free_neighbor]['domain'])
        else:
            print('no value for neighbors', free_neighbor)
            G.nodes[node]['domain'].remove(G.nodes[node]['label'])
            G.nodes[node]['label'] = ''
    else: #do nothing
        pass
    
    return G

def everything_ok(G):
    for node in G.nodes():
        G = check_consistency(G, node, G.nodes[node]['shape'])
        
        for node_1 in G.nodes():
            if G.nodes[node_1]['label'] == '':
                return False
    return True
    

def show_graph(G):
    nodePos = nx.layout.spring_layout(G)
    nodeShapes = set((aShape[1]['shape'] for aShape in G.nodes(data = True)))
    nodeLabels = {}
    i = 0
    for aShape in G.nodes(data = True):
        nodeLabels[i] = (aShape[1]['label'])
        i = i + 1
    for aShape in nodeShapes:
        nx.draw_networkx_nodes(G,nodePos,node_shape = aShape, with_labels = True, font_weight = 'bold', nodelist = [sNode[0] for sNode in filter(lambda x: x[1]['shape']==aShape,G.nodes(data = True))])
    nx.draw_networkx_labels(G,nodePos, nodeLabels)
    nx.draw_networkx_edges(G,nodePos)
    plt.show()

#check whether all of them are assigned
def finished(G):
    for node in G.nodes():
        if G.nodes[node]['label'] == '':
            return False
    return True #in case all of them are assigned

#check whether we have any domain or not!
def failed(G):
    for node in G.nodes():
        if len(G.nodes[node]['domain']) != 0:
            return False
    return True

def select_next_free_node(G, mode): #mode=1 for normal selection and mode=2 for MRV
    if mode == 1:
        for node in G.nodes():
            if G.nodes[node]['label'] == '':
                return node
    elif mode == 2:
        max_dom_size = 10
        max_dom_node = None
        for node in G.nodes():
            if G.nodes[node]['label'] == '':
                if len(G.nodes[node]['domain']) < max_dom_size:
                    max_dom_size = len(G.nodes[node]['domain'])
                    max_dom_node = node
        return max_dom_node #return the node

def bt_fc(G, mode, assignment_queue = []): #mode=1 for normal selection, mode=2 for MRV selection
    print('BT-FC')
    assignment_queue = []#used for backtracking
    while finished(G) == False: #while we haven't assigned everything
        if failed(G) == False:
            node = select_next_free_node(G, mode)
            print(G.nodes[node])
            node_domain = G.nodes[node]['domain']
            for value in node_domain:
                G.nodes[node]['label'] = value
                #FC Code
                
                #case '^'
                if G.nodes[node]['shape'] == '^':
                    G = check_consistency(G, node, '^')
                #case 's'
                elif G.nodes[node]['shape'] == 's':
                    G = check_consistency(G, node, 's')

                #case 'p'
                elif G.nodes[node]['shape'] == 'p':
                    G = check_consistency(G, node, 'p')

                #case 'h'
                elif G.nodes[node]['shape'] == 'h':
                    G = check_consistency(G, node, 'h')

                if(G.nodes[node]['label'] == ''):
                    print('previous value not acceptable!')
                    #time.sleep(1)
                else:
                    print('value', value,'assigned to',node, ' and fc checked succesfully!')
                    #show_graph(G)
                    break

            if(G.nodes[node]['label'] != ''):
                print('phew!', node, 'has value', G.nodes[node]['label'], '. no need for backtracking!')
                print(G.nodes[node]['domain'])
                if not node in assignment_queue:
                    assignment_queue.append(node)#storing it for backtracking(if we reach a deadend!)
            else:
                print('uh oh!', node, 'doesn\'t have any value. we must backtrack!')
                print('moving to assignment queue')
                print('assignment queue is', assignment_queue)
                if(len(assignment_queue) != 0):
                    G.nodes[node]['domain'] = [1, 2, 3, 4, 5, 6, 7, 8, 9]#restoring domain for the node without answer
                    G.nodes[assignment_queue[-1]]['domain'].remove(G.nodes[assignment_queue[-1]]['label'])
                    print('domain for troubling node changed to', G.nodes[assignment_queue[-1]]['domain'])
                    G.nodes[assignment_queue[-1]]['label'] = ''
                    assignment_queue.pop() #this means that the prevoius node is no longer assigned!
                else:
                    print('sorry! tried everything and nothing left to backtrack :( ')
                    show_graph(G)
                    return -1
        else:
            print('sorry! this graph has no answer :( ')
            show_graph(G)
            break
    if everything_ok(G) == True:
        print('congratulations! we have reached an answer :) ')
        show_graph(G)
    else:
        for node in G.nodes():
            G.nodes[node]['label'] = ''
        print('sorry! this graph has no answer :( ')
        show_graph(G)
    

print('Welcome to the World of Shapes!')
time.sleep(1)
nodes = []
edges = []

print('Enter vertice size: ')
v_size = int(input())
print('Enter edge size: ')
e_size = int(input())

print('Enter vertices: ')
vertice_string = input().split()
for i in range(len(vertice_string)):
    nodes.append(vertice_string[i])

print('Enter edges: ')
for i in range(e_size):
    edge = []
    edge_string = input().split()
    edge.append(int(edge_string[0]))
    edge.append(int(edge_string[1]))
    edges.append(edge)

G = nx.Graph()

#graph vertices
for i in range(len(nodes)):
    if nodes[i] == 'T':
        G.add_node(i, shape = '^', label = '', domain = [1, 2, 3, 4, 5, 6, 7, 8, 9])
    elif nodes[i] == 'S':
        G.add_node(i, shape = 's', label = '', domain = [1, 2, 3, 4, 5, 6, 7, 8, 9])
    elif nodes[i] == 'P':
        G.add_node(i, shape = 'p', label = '', domain = [1, 2, 3, 4, 5, 6, 7, 8, 9])
    elif nodes[i] == 'H':
        G.add_node(i, shape = 'h', label = '', domain = [1, 2, 3, 4, 5, 6, 7, 8, 9])
    elif nodes[i] == 'C':
        G.add_node(i, shape = 'o', label = '', domain = [1, 2, 3, 4, 5, 6, 7, 8, 9])

#graph edges
for i in range(len(edges)):
    G.add_edge(edges[i][0], edges[i][1])

for i in range(1):
    print('Select 1 for BT-FC or 2 for BT-FC-MRV')
    mode = input()
    if(mode != '1' and mode != '2'):
        print('Please try again!')
        i = i - 1
mode_int = int(mode)


if mode_int == 1:
    bt_fc(G, 1) #incremental selection
if mode_int == 2:
    bt_fc(G, 2) #MRV selection