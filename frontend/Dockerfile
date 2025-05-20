FROM node:22-slim
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
EXPOSE 3000
ENTRYPOINT ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "3000"]
