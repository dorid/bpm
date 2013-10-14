<#assign findNode= "newp.FindNodeMethod"?new()>
digraph process_diagram {
rankdir=LR;
splines=line;
overlap="0:";
/* sep="+25,25";
overlap=scalexy;
nodesep=0.6; */
outputMode=nodesfirst;
node [
fixedsize=true, pin=true, fontsize=9,
shape=box, style="filled",
fillcolor="#fafad2", fontname="Monospace",
color="#333333", forcelabels="false"
];
edge [
forcelabels="true"
];

<#macro paint currentNode nodeList index>
N_${index}[label="${node.name}"]
    <#list currentNode.childList as child>
        <#if child.nodeName=='outgoing'>
            <#assign nextLine = findNode(nodeList, "id", child.value)>
            <#assign nextNode = findNode(nodeList, "targetRef", nextLine.value)>
        </#if>
    </#list>
    <@paint currentNode=currentNode nodeList=nodeList index=index></@paint>
</#macro>


<#list nodeList as currentNode>
    <#if currentNode.nodeName == 'startEvent'>
        <@paint currentNode=currentNode nodeList=nodeList index=currentNode_index></@paint>
    </#if>
</#list>

}
