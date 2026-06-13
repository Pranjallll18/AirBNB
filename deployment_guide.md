# 🚀 AirBnb Backend — Deployment Guide

Since you've pushed the code to GitHub, the easiest way to deploy your Spring Boot backend so it is live 24/7 is using **Railway** or **Render**. 

Below are the step-by-step instructions for both platforms.

---

## Option 1: Railway (Recommended)
Since your project's `application.properties` was already configured for Railway's database format, this is the easiest option.

### Step 1: Create a Railway Account & Project
1. Go to [Railway.app](https://railway.app) and sign up with your GitHub account.
2. Click **New Project** → select **Provision MySQL**.
   *This provisions a live MySQL database in the cloud.*

### Step 2: Deploy the Spring Boot App
1. Inside your Railway project canvas, click **New** (or `+` symbol) → select **Github Repo**.
2. Select your `AirBNB` repository.
3. Railway will automatically detect the `Dockerfile` in your repository and start building and deploying the container.

### Step 3: Link App to the Database
To let the backend connect to the MySQL database, we need to pass the database credentials to the Spring Boot app:
1. Click on your deployed **GitHub Repo service** box in the Railway dashboard.
2. Go to the **Variables** tab.
3. Click **Add Variable** and reference the database variables provided by Railway's MySQL database. Add the following variables:
   * `DB_URL` = `jdbc:mysql://${{MYSQLHOST}}:${{MYSQLPORT}}/${{MYSQLDATABASE}}`
   * `DB_USERNAME` = `${{MYSQLUSER}}`
   * `DB_PASSWORD` = `${{MYSQLPASSWORD}}`
   * `JPA_DDL_AUTO` = `update`
   * `JWT_SECRET_KEY` = *[Generate a random long string]*
   * `STRIPE_SECRET_KEY` = *[Your Stripe Test Secret Key]*
   * `STRIPE_WEBHOOK_SECRET` = *[Your Stripe Webhook Secret Key]*
4. Railway will redeploy your app automatically.
5. In the **Settings** tab of your app service, under **Networking**, click **Generate Domain**. You will get a public URL (e.g. `https://airbnb-production.up.railway.app`).

---

## Option 2: Render (Completely Free Tier)
Render offers a completely free tier for hosting web services using Docker.

### Step 1: Create a PostgreSQL or MySQL Database
*Render offers a free PostgreSQL database for 90 days, or you can use a free MySQL database from providers like [Aiven](https://aiven.io/) or [Tidb Cloud](https://pingcap.com/products/tidb-cloud).*

1. Go to [Render.com](https://render.com) and log in with GitHub.
2. Click **New** → **PostgreSQL**.
3. Name it, select the **Free** tier, and click **Create Database**.
4. Copy the **External Database URL** (e.g. `postgresql://...`).

### Step 2: Deploy the Web Service
1. On Render dashboard, click **New** → **Web Service**.
2. Connect your GitHub repository.
3. Configure the service:
   * **Name**: `airbnb-backend`
   * **Region**: Select the region closest to you.
   * **Runtime**: Select **Docker** (it will use the multi-stage Dockerfile we optimized).
   * **Instance Type**: Select **Free**.
4. Scroll down and click **Advanced** to add **Environment Variables**:
   * `DB_URL` = `jdbc:postgresql://[your-database-host]:5432/[your-database-name]` *(Modify based on your Render database credentials)*
   * `DB_USERNAME` = `[your-database-user]`
   * `DB_PASSWORD` = `[your-database-password]`
   * `JPA_DDL_AUTO` = `update`
   * `JWT_SECRET_KEY` = `[Your JWT Secret Key]`
   * `STRIPE_SECRET_KEY` = `[Your Stripe Secret Key]`
5. Click **Create Web Service**. Render will pull the code, build the Docker image, and deploy it.

---

## 🎯 Verification after Deployment
Once deployed, verify that the application is online:
1. Append `/api/v1/swagger-ui/index.html` to your generated deployment URL (e.g. `https://airbnb-backend.onrender.com/api/v1/swagger-ui/index.html`).
2. Verify that the Swagger documentation page loads correctly.
