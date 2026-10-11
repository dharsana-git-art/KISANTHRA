FROM node:22-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
ARG VITE_API_BASE=http://localhost:8081
ARG VITE_FARMER_API=http://localhost:8082
ARG VITE_LOAN_API=http://localhost:8084
ENV VITE_API_BASE=$VITE_API_BASE VITE_FARMER_API=$VITE_FARMER_API VITE_LOAN_API=$VITE_LOAN_API
RUN npm run build
FROM node:22-alpine
WORKDIR /app
COPY --from=build /app .
EXPOSE 5173
CMD ["npm","run","dev"]
