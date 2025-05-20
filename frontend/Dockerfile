FROM node:22-slim AS dev
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
EXPOSE 3000
ENTRYPOINT ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "3000"]

FROM node:22-slim AS prod
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
EXPOSE 3000
ENTRYPOINT ["npm", "run", "start"]
