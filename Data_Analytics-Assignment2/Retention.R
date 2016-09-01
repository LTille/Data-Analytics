retention = read.csv("/Users/Tillie/Desktop/retention.csv", header=TRUE)
retention[1:3,]
summary(retention)

rm=(list=ls())
#list all the statistical information for variables
Rowname = c("spend","apret","top10","rejr","tstsc","pacc","strat","salar");
Columname = c("Min","1stQuartile","Median","Mean","3rdQuartile","Max","SD","Variance","Count")
spend = c(as.vector(summary(retention$spend)), sd(retention$spend),var(retention$spend),length(retention$spend))
apret = c(as.vector(summary(retention$apret)), sd(retention$apret),var(retention$apret),length(retention$apret))
top10 = c(as.vector(summary(retention$top10)), sd(retention$top10),var(retention$top10),length(retention$top10))
rejr = c(as.vector(summary(retention$rejr)), sd(retention$rejr),var(retention$rejr),length(retention$rejr))
tstsc = c(as.vector(summary(retention$tstsc)), sd(retention$tstsc),var(retention$tstsc),length(retention$tstsc))
pacc = c(as.vector(summary(retention$pacc)), sd(retention$pacc),var(retention$pacc),length(retention$pacc))
strat = c(as.vector(summary(retention$strat)), sd(retention$strat),var(retention$strat),length(retention$strat))
salar = c(as.vector(summary(retention$salar)), sd(retention$salar),var(retention$salar),length(retention$salar))

Summary = matrix(c(spend,apret,top10,rejr,tstsc,pacc,strat,salar),nrow=8,ncol=9,byrow=TRUE,dimnames = list(Rowname,Columname))
Summary

#plot histogram for apret, tstsc, and salar
library('ggplot2')
theme_set((theme_bw()))
ggplot(retention, aes(x=apret))+geom_histogram(binwidth=10)
ggplot(retention, aes(x=tstsc))+geom_histogram(binwidth=0.5)
ggplot(retention, aes(x=salar))+geom_histogram(binwidth=0.5)

#perform linear regression of apret on tstsc 
fit1=lm(apret~tstsc,data=retention)
summary(fit1)
ggplot(retention, aes(x=tstsc,y=apret))+geom_point()+geom_smooth(method=lm,se=FALSE)

#perform linear regression of apret on salar 
fit2=lm(apret~salar,data=retention)
summary(fit2)
ggplot(retention, aes(x=salar,y=apret))+geom_point()+geom_smooth(method=lm,se=FALSE)

#perform linear regression of apret on both tstsc and salar.
fit3=lm(apret~tstsc+salar,data=retention)
summary(fit3)

##compute RMSE for the retention data
model.mse = mean(residuals(fit3)^2)
rmse = sqrt (model.mse)
rmse

cor(retention)
library('car')
suppressWarnings( ## (avoid printing the warnings)
  scatterplotMatrix(retention, spread=FALSE, lty.smooth=2,
                    main="Scatter Plot Matrix")
)
